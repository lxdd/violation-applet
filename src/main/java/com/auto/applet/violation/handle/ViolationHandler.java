package com.auto.applet.violation.handle;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.Duration;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.auto.applet.violation.common.bo.CarList;
import com.auto.applet.violation.common.bo.LicenseBO;
import com.auto.applet.violation.common.bo.ViolationBO;
import com.auto.applet.violation.common.constant.Constant;
import com.auto.applet.violation.common.constant.LimitTypeEnum;
import com.auto.applet.violation.common.constant.OperTypeEnum;
import com.auto.applet.violation.common.constant.ReturnEnum;
import com.auto.applet.violation.common.support.Result;
import com.auto.applet.violation.common.util.DateUtil;
import com.auto.applet.violation.common.util.JsonUtil;
import com.auto.applet.violation.common.vo.BindCarVO;
import com.auto.applet.violation.common.vo.LicenseVO;
import com.auto.applet.violation.common.vo.UnBindCarVO;
import com.auto.applet.violation.common.vo.ViolationVO;
import com.auto.applet.violation.dao.CarRepository;
import com.auto.applet.violation.dao.LicenseOuterRepository;
import com.auto.applet.violation.dao.LimitRepository;
import com.auto.applet.violation.dao.LogRepository;
import com.auto.applet.violation.dao.UserCarMappingRepository;
import com.auto.applet.violation.dao.UserRepository;
import com.auto.applet.violation.dao.ViolationOuterRepository;
import com.auto.applet.violation.model.Car;
import com.auto.applet.violation.model.Limit;
import com.auto.applet.violation.model.Logs;
import com.auto.applet.violation.model.User;
import com.auto.applet.violation.model.UserCar;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author li_xiaodong
 * @date 2018年7月21日
 */
@Service
public class ViolationHandler {

	/**
	 * 日志
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private UserCarMappingRepository userCarMappingRepository;

	@Autowired
	private LimitRepository limitRepository;

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private ViolationOuterRepository violationOuterRepository;

	@Autowired
	private LicenseOuterRepository licenseOuterRepository;

	/**
	 * 用户当日可查询违章次数
	 */
	@Value("${limit.query}")
	private Integer queryNumberLimit;

	/**
	 * 用户可绑定车辆数
	 */
	@Value("${limit.bind}")
	private Integer bindNumberLimit;

	/**
	 * bindCar 绑定车辆
	 * <p>
	 * <p>
	 * 1、检查入参cSideUserId、number，无，return；
	 * <p>
	 * 2、调违章服务，通过查违章返回信息，判定车辆信息是否对，不对，return；
	 * <p>
	 * 4、查用户是否存在，不存在，则新建用户，同时新建用户limit（使用限制表）；
	 * <p>
	 * 4、查车辆是否存在，不存在，则新建车辆；
	 * <p>
	 * 5、查人车绑定是否存在，不存在，则新建人车绑定，
	 * <p>
	 * 6、同时 更新 已用车辆数与已用查询次数；（doOnNext） 7、非首次使用的用户，车辆绑定信息有误的，违章查询次数+1；
	 * <p>
	 * TODO 如果用户是初次绑定，且信息有误，则这次违章查询次数将无法被计算。
	 * <p>
	 * TODO 虽然另外提供是否可绑定接口，但此接口也应该做校验
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> bindCar(ServerRequest request) {

		return transReqBindCar(request).flatMap(addCarVO -> {

			return checkBindCar(addCarVO).flatMap(result -> {

				if (!ReturnEnum.SUCCESS.getReturnCode().equals(result.getCode())) {

					// LOG
					addLog(addCarVO.getcSideUserId(), "", OperTypeEnum.BIND_CAR.getOperTypeId(), result.getCode(),
							result.getMsg());

					return ok().body(Mono.just(result), Result.class);
				} else {

					return addUser(addCarVO.getcSideUserId())
							.flatMap(userVO -> addCar(addCarVO.getNumber(), addCarVO.getVin(), addCarVO.getEngine())
									.flatMap(carVO -> addUserCar(addCarVO.getcSideUserId(), carVO.getId(),
											addCarVO.getOpenSmsRemind())))
							.flatMap(userCar -> {

								ViolationBO bo = result.getData();
								bo.setCarId(userCar.getCarId());
								result.setData(bo);

								// LOG
								addLog(userCar.getcSideUserId(), userCar.getCarId(),
										OperTypeEnum.BIND_CAR.getOperTypeId(), result.getCode(), result.getMsg());

								return ok().body(Mono.just(result), Result.class);
							}).log();

				}

			});

		});

	}

	/**
	 * listCar 车辆列表
	 * <p>
	 * 1、检查入参cSideUserId，无，return；
	 * <p>
	 * 2、查询用户，无，return；
	 * <p>
	 * 3、查询人车绑定，无，return；
	 * <p>
	 * 4、查询车辆信息，无，return；
	 * <p>
	 * 5、查车辆违章服务；
	 * <p>
	 * 6、merge车辆与违章信息；
	 * <p>
	 * 7、更新 已用车辆数与已用查询次数；（doOnNext）
	 * <p>
	 * TODO 此接口需要做查询次数限制，如果发现超过次数，则不做调用违章服务
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> listCar(ServerRequest request) {

		return transReqListCar(request)
				.flatMap(cSideUserId -> userRepository.findByCSideUserIdAndIsDeleted(cSideUserId, Constant.NOT_DELETED)
						.flatMap(user -> ok().body(userCarMappingRepository
								.findByCSideUserIdAndIsDeleted(user.getcSideUserId(), Constant.NOT_DELETED)

								.flatMap(userCar -> carRepository
										.findByIdAndIsDeleted(userCar.getCarId(), Constant.NOT_DELETED)
										.flatMap(car -> mergeViolation(userCar, car)).doOnNext(userCarVo -> {

											// updateLimit
											updateLimit(userCar.getcSideUserId(), OperTypeEnum.CAR_LIST).subscribe();

											// LOG
											addLog(userCar.getcSideUserId(), userCar.getCarId(),
													OperTypeEnum.CAR_LIST.getOperTypeId(),
													ReturnEnum.SUCCESS.getReturnCode(),
													ReturnEnum.SUCCESS.getReturnMsg());

										})),
								CarList.class)

		));

	}

	/**
	 * unBindCar 解绑车辆
	 * 
	 * <p>
	 * 1、检查入参cSideUserId、carId，无，return；
	 * <p>
	 * 2、查询用户，无，return；
	 * <p>
	 * 3、查询人车绑定，无，return；
	 * <p>
	 * 4、解绑人车关系；
	 * <p>
	 * 5、更新 已用车辆数；（doOnNext）
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> unBindCar(ServerRequest request) {

		return transReqUnBindCar(request).flatMap(vo -> userRepository
				.findByCSideUserIdAndIsDeleted(vo.getcSideUserId(), Constant.NOT_DELETED)
				.flatMap(user -> userCarMappingRepository.findByCSideUserIdAndCarIdAndIsDeleted(user.getcSideUserId(),
						vo.getCarId(), Constant.NOT_DELETED).flatMap(userCar -> deleteUserCar(userCar))))
				.flatMap(userCar -> {

					// LOG
					addLog(userCar.getcSideUserId(), userCar.getCarId(), OperTypeEnum.UN_BIND_CAR.getOperTypeId(),
							ReturnEnum.SUCCESS.getReturnCode(), ReturnEnum.SUCCESS.getReturnMsg());

					return ok().body(Mono.just(new Result<>()), Result.class);

				});
	}

	/**
	 * violation 查询违章--一键查询
	 * <p>
	 * 1、检查入参cSideUserId、carId，无，return；
	 * <p>
	 * 2、查询人车绑定，无，return；
	 * <p>
	 * 3、查询车辆信息，无，return；
	 * <p>
	 * 4、判定当日是否有查询限额记录，无则新增当日违章记录；
	 * <p>
	 * 5、判定是否已达到当日可查询违章次数，达到则，return；
	 * <p>
	 * 6、未达到，则当日违章查询次数+1；
	 * <p>
	 * 7、查车辆违章服务；
	 * <p>
	 * 8、记录日志；
	 *
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> violation(ServerRequest request) {

		return transReqViolation(request).flatMap(vo -> {

			// 查询违章
			return ok().body(userCarMappingRepository
					.findByCSideUserIdAndCarIdAndIsDeleted(vo.getcSideUserId(), vo.getCarId(), Constant.NOT_DELETED)
					.flatMap(userCar -> carRepository.findById(userCar.getCarId())).flatMap(car -> {

						// 判定当日是否有查询限额记录
						Flux<Limit> limitFlux = limitRepository
								.findByCSideUserIdAndIsDeleted(vo.getcSideUserId(), Constant.NOT_DELETED)
								.filter(limitVO -> LimitTypeEnum.LIMIT_USER_QUERY_NUMBER.getLimitTypeId()
										.equals(limitVO.getLimitType())
										&& DateUtil.inSameDate(limitVO.getGmtCreate(), new Date()));
						return limitFlux.switchIfEmpty(limit -> addLimitDay(vo.getcSideUserId()).subscribe())
								.elementAt(0).flatMap(limit -> {

									// 已达到当日可查询违章限额
									if (limit.getLimitNum() <= limit.getUseNum()) {

										Result<ViolationBO> result = new Result<>();

										result.setReturn(ReturnEnum.VIOLATION_LIMIT_QUERY_ONE_DAY);
										// LOG
										addLog(vo.getcSideUserId(), vo.getCarId(),
												OperTypeEnum.QUERY_VIOLATION.getOperTypeId(), result.getCode(),
												result.getMsg());
										return Mono.just(result);
									}

									// 更新当日查询次数
									limit.setUseNum(limit.getUseNum() + 1);
									limit.setGmtModified(new Date());
									limitRepository.save(limit).subscribe();

									// 查询违章服务
									Result<ViolationBO> violationResult = violationOuterRepository.getViolation(
											car.getEngine(), car.getNumber(), car.getVin(), vo.getcSideUserId(),
											vo.getCarId());

									// 将违章信息 存入车辆表
									carRepository.findByIdAndIsDeleted(vo.getCarId(), Constant.NOT_DELETED)
											.doOnNext(carVO -> {
												carVO.setViolation(JsonUtil.parseToJson(violationResult.getData()));
												carVO.setGmtModified(new Date());
												carRepository.save(car).subscribe();
											});

									// 更新人车关系表 的最近查询时间
									userCarMappingRepository.findByCSideUserIdAndCarIdAndIsDeleted(vo.getcSideUserId(),
											vo.getCarId(), Constant.NOT_DELETED).doOnNext(userCarVO -> {
												userCarVO.setGmtLatelyQuery(new Date());
												userCarVO.setGmtModified(new Date());
												userCarMappingRepository.save(userCarVO);
											});

									// LOG
									addLog(vo.getcSideUserId(), vo.getCarId(),
											OperTypeEnum.QUERY_VIOLATION.getOperTypeId(), violationResult.getCode(),
											JsonUtil.parseToJson(violationResult.getData()));

									return Mono.just(violationResult);

								});

					}

			), Result.class);

		});

	}

	/**
	 * license 行驶证识别
	 * 
	 * <p>
	 * 调外部行驶证识别HTTP接口，返回识别结果
	 * 
	 * 
	 * <p>
	 * 二、测试case
	 * <p>
	 * 1、传入清晰的行驶证图片URL，失败出车牌、车架、发动机号任意一个有值，则识别成功，否则及认为识别失败；（要求url存储在公有桶）
	 * <p>
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> license(ServerRequest request) {

		return transReqLicense(request).flatMap(vo -> {

			Result<LicenseBO> result = licenseOuterRepository.license(vo.getLince());

			// LOG
			addLog(vo.getcSideUserId(), "", OperTypeEnum.LICENSE.getOperTypeId(), result.getCode(),
					result.getMsg() + "-" + JsonUtil.parseToJson(result.getData()));

			return ok().body(Mono.just(result), Result.class);
		});

	}

	/**
	 * limitAddCar 是否可添加车辆
	 * <p>
	 * 一、功能
	 * <p>
	 * 1、cSideUserId在系统不存在，返回true；
	 * <p>
	 * 2、cSideUserId在系统存在，可绑定车辆数<已绑定车辆数，返回true
	 * <p>
	 * 3、cSideUserId在系统存在，可绑定车辆数>=已绑定车辆数，返回false
	 * 
	 * 
	 * <p>
	 * 二、测试case
	 * <p>
	 * 1、如果未传cSideUserId，则返回false
	 * <p>
	 * 2、如果所传cSideUserId在系统不存在，则返回true；
	 * <p>
	 * 3、cSideUserId在系统存在，可绑定车辆数>=已绑定车辆数，返回false；
	 * <p>
	 * 4、cSideUserId在系统存在，可绑定车辆数<已绑定车辆数，返回true；
	 * <p>
	 * s
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> limitAddCar(ServerRequest request) {

		String cSideUserId = request.headers().header("cSideUserId").isEmpty() ? ""
				: request.headers().header("cSideUserId").get(0);

		Result<Boolean> result = new Result<>();

		return Mono.just(cSideUserId).flatMap(userId -> {

			if (StringUtils.isEmpty(userId)) {
				result.setData(false);

				return ok().body(Mono.just(result), Result.class);
			}

			return limitRepository.findByCSideUserIdAndIsDeleted(cSideUserId, Constant.NOT_DELETED)
					.filter(limitVO -> LimitTypeEnum.LIMIT_USER_ENABLE_BIND_CAR_NUMBER.getLimitTypeId()
							.equals(limitVO.getLimitType()))
					.switchIfEmpty(Mono.just(
							new Limit(cSideUserId, LimitTypeEnum.LIMIT_USER_ENABLE_BIND_CAR_NUMBER.getLimitTypeId(),
									bindNumberLimit, Constant.LIMIT_USE_NUMBER)))
					.elementAt(0).flatMap(limit -> {

						// 已达到可绑定车辆限额
						if (limit.getLimitNum() <= limit.getUseNum()) {
							result.setData(false);
						} else {
							result.setData(true);
						}

						// LOG
						addLog(cSideUserId, "", OperTypeEnum.LIMIT_ADD_CAR.getOperTypeId(),
								result.getData() ? ReturnEnum.SUCCESS.getReturnCode()
										: ReturnEnum.LIMIT_ADD_CAR_NO.getReturnCode(),
								result.getData() ? Constant.LIMIT_ADD_CAR_YES
										: ReturnEnum.LIMIT_ADD_CAR_NO.getReturnMsg());

						return ok().body(Mono.just(result), Result.class);

					});

		});

	}

	/**
	 * mergeViolation 查询违章服务
	 * 
	 * @param userCar
	 * @param car
	 * @return
	 */
	private Mono<CarList> mergeViolation(UserCar userCar, Car car) {

		CarList carli = new CarList();

		Result<ViolationBO> violationResult = violationOuterRepository.getViolation(car.getEngine(), car.getNumber(),
				car.getVin(), userCar.getcSideUserId(), car.getId());

		if (ReturnEnum.SUCCESS.getReturnCode().equals(violationResult.getCode())) {
			BeanUtils.copyProperties(violationResult.getData(), carli);
		}

		// 将违章信息 存入车辆表
		carRepository.findByIdAndIsDeleted(car.getId(), Constant.NOT_DELETED).doOnNext(carVO -> {
			carVO.setViolation(JsonUtil.parseToJson(violationResult.getData()));
			carVO.setGmtModified(new Date());
			carRepository.save(car).subscribe();
		});

		// 更新人车关系表 的最近查询时间
		userCarMappingRepository
				.findByCSideUserIdAndCarIdAndIsDeleted(userCar.getcSideUserId(), car.getId(), Constant.NOT_DELETED)
				.doOnNext(userCarVO -> {
					userCarVO.setGmtLatelyQuery(new Date());
					userCarVO.setGmtModified(new Date());
					userCarMappingRepository.save(userCarVO);
				});

		carli.setNumber(car.getNumber());
		carli.setVin(car.getVin());
		carli.setEngine(car.getEngine());
		carli.setCarId(car.getId());

		return Mono.just(carli);
	}

	/**
	 * checkBindCar 检查绑定车辆信息
	 * <p>
	 * 1、检查参数
	 * <p>
	 * 2、查询违章服务，如果查询结果信息有误，则仍需记录查询次数+1
	 * <p>
	 * 3、返回违章信息
	 * 
	 * @param addCarVO
	 * @return
	 */
	private Mono<Result<ViolationBO>> checkBindCar(BindCarVO addCarVO) {

		// 构建返回
		Result<ViolationBO> result = new Result<>();

		if (null == addCarVO) {
			result.setReturn(ReturnEnum.EMPTY_PARAM_BODY);
			return Mono.just(result);

		}
		if (StringUtils.isEmpty(addCarVO.getcSideUserId())) {
			result.setReturn(ReturnEnum.EMPTY_PARAM_USER_ID);
			return Mono.just(result);

		}
		if (StringUtils.isEmpty(addCarVO.getNumber())) {
			result.setReturn(ReturnEnum.EMPTY_PARAM_NUMBER);
			return Mono.just(result);
		}

		// 先判定 用户绑定车辆数 是否超出
		Mono<Result<ViolationBO>> resultMono = limitRepository
				.findByCSideUserIdAndIsDeleted(addCarVO.getcSideUserId(), Constant.NOT_DELETED)
				.filter(limitVO -> LimitTypeEnum.LIMIT_USER_ENABLE_BIND_CAR_NUMBER.getLimitTypeId()
						.equals(limitVO.getLimitType()))
				.switchIfEmpty(Mono.just(new Limit(addCarVO.getcSideUserId(),
						LimitTypeEnum.LIMIT_USER_ENABLE_BIND_CAR_NUMBER.getLimitTypeId(), bindNumberLimit,
						Constant.LIMIT_USE_NUMBER)))
				.elementAt(0).flatMap(limit -> {

					// 已达到可绑定车辆限额
					if (limit.getLimitNum() <= limit.getUseNum()) {
						result.setReturn(ReturnEnum.LIMIT_ADD_CAR);

						// LOG
						addLog(addCarVO.getcSideUserId(), "", OperTypeEnum.LIMIT_ADD_CAR.getOperTypeId(),
								result.getCode(), result.getMsg());
						// 未达到
					} else {

						// 包装一个同步阻塞的调用
						// 1、使用 fromCallable 方法生成一个 Mono；
						Mono<Result<ViolationBO>> blockingWrapper = Mono.fromCallable(() -> {

							// 2、返回同步、阻塞的资源；
							return violationOuterRepository.getViolation(addCarVO.getEngine(), addCarVO.getNumber(),
									addCarVO.getVin(), addCarVO.getcSideUserId(), null);
						});

						// 3、 使用 Schedulers.elastic() 确保对每一个订阅来说运行在一个专门的线程上。
						blockingWrapper = blockingWrapper.subscribeOn(Schedulers.elastic());

						// 4、在拿到元素前阻塞：Mono#block …​并给出超时时限
						Result<ViolationBO> violationResult = blockingWrapper.block(Duration.ofSeconds(60));
						
						// 此处需给用户 当日违章查询次数 +1
						limitRepository
								.findByCSideUserIdAndIsDeleted(addCarVO.getcSideUserId(), Constant.NOT_DELETED)
								.doOnNext(vo -> {
									updateLimit(addCarVO.getcSideUserId(), OperTypeEnum.BIND_CAR_FAIL).subscribe();
								}).subscribe();

						if (ReturnEnum.SUCCESS.getReturnCode().equals(violationResult.getCode())) {
							result.setData(violationResult.getData());
						} else {
							result.setCode(violationResult.getCode());
							result.setMsg(violationResult.getMsg());
						}

						return Mono.just(result);

					}

					return Mono.just(result);

				});

		return resultMono;

	}

	/**
	 * transReqBindCar
	 * 
	 * @param request
	 * @return
	 */
	private Mono<BindCarVO> transReqBindCar(ServerRequest request) {

		String cSideUserId = request.headers().header("cSideUserId").isEmpty() ? ""
				: request.headers().header("cSideUserId").get(0);

		return request.bodyToMono(BindCarVO.class).flatMap(bindCarVO -> {

			bindCarVO.setcSideUserId(cSideUserId);

			return Mono.just(bindCarVO);
		});

	}

	/**
	 * @param request
	 * @return
	 */
	private Mono<UnBindCarVO> transReqUnBindCar(ServerRequest request) {

		String cSideUserId = request.headers().header("cSideUserId").isEmpty() ? ""
				: request.headers().header("cSideUserId").get(0);

		String carId = request.pathVariable("carId");

		if (StringUtils.isEmpty(cSideUserId) || StringUtils.isEmpty(carId)) {
			return Mono.empty();
		}

		return Mono.just(new UnBindCarVO(cSideUserId, carId));

	}

	/**
	 * @param request
	 * @return
	 */
	private Mono<String> transReqListCar(ServerRequest request) {
		return Mono.justOrEmpty(request.headers().header("cSideUserId").isEmpty() ? ""
				: request.headers().header("cSideUserId").get(0));
	}

	/**
	 * @param request
	 * @return
	 */
	private Mono<ViolationVO> transReqViolation(ServerRequest request) {
		String cSideUserId = request.headers().header("cSideUserId").isEmpty() ? ""
				: request.headers().header("cSideUserId").get(0);

		String carId = request.pathVariable("carId");

		return Mono.just(new ViolationVO(cSideUserId, carId));
	}

	private Mono<LicenseVO> transReqLicense(ServerRequest request) {

		String cSideUserId = request.headers().header("cSideUserId").isEmpty() ? ""
				: request.headers().header("cSideUserId").get(0);

		return request.bodyToMono(LicenseVO.class).flatMap(licenseVO -> {

			licenseVO.setcSideUserId(cSideUserId);

			return Mono.just(licenseVO);
		});

	}

	/**
	 * addUser
	 * 
	 * @param cSideUserId
	 * @return
	 */
	private Mono<User> addUser(String cSideUserId) {

		return userRepository.findByCSideUserIdAndIsDeleted(cSideUserId, Constant.NOT_DELETED)
				.switchIfEmpty(userRepository.save(new User(cSideUserId))
						.doOnNext(userVO -> addLimit(userVO.getcSideUserId()).subscribe()));

	}

	/**
	 * @param number
	 * @param vin
	 * @param engine
	 * @return
	 */
	private Mono<Car> addCar(String number, String vin, String engine) {

		return carRepository.findByNumberAndIsDeleted(number, Constant.NOT_DELETED)
				.switchIfEmpty(carRepository.save(new Car(number, vin, engine)));
	}

	/**
	 * addUserCar
	 * 
	 * @param cSideUserId
	 * @param carId
	 * @param openSmsRemind
	 * @return
	 */
	private Mono<UserCar> addUserCar(String cSideUserId, String carId, Integer openSmsRemind) {

		return userCarMappingRepository.findByCSideUserIdAndCarIdAndIsDeleted(cSideUserId, carId, Constant.NOT_DELETED)
				.switchIfEmpty(userCarMappingRepository.save(new UserCar(cSideUserId, carId, openSmsRemind))
						.doOnNext(userCar -> updateLimit(userCar.getcSideUserId(), OperTypeEnum.BIND_CAR).subscribe()));
	}

	/**
	 * deleteUserCar(UserCar)
	 * 
	 * @param userCar
	 * @return
	 */
	private Mono<UserCar> deleteUserCar(UserCar userCar) {

		userCar.setIsDeleted(Constant.DELETED);
		userCar.setGmtModified(new Date());

		return userCarMappingRepository.save(userCar)
				.doOnNext(userCarVo -> updateLimit(userCarVo.getcSideUserId(), OperTypeEnum.UN_BIND_CAR).subscribe());
	}

	/**
	 * addLimit
	 * 
	 * @param cSideUserId
	 * @return
	 */
	private Flux<Limit> addLimit(String cSideUserId) {

		Limit limitQuery = new Limit(cSideUserId, LimitTypeEnum.LIMIT_USER_QUERY_NUMBER.getLimitTypeId(),
				queryNumberLimit, Constant.LIMIT_USE_NUMBER);

		Limit limitBind = new Limit(cSideUserId, LimitTypeEnum.LIMIT_USER_ENABLE_BIND_CAR_NUMBER.getLimitTypeId(),
				bindNumberLimit, Constant.LIMIT_USE_NUMBER);

		return limitRepository.saveAll(Flux.just(limitQuery, limitBind));
	}

	/**
	 * addLimitDay 新建用户单日查询记录
	 * 
	 * @param cSideUserId
	 * @return
	 */
	private Mono<Limit> addLimitDay(String cSideUserId) {

		Limit limitQuery = new Limit(cSideUserId, LimitTypeEnum.LIMIT_USER_QUERY_NUMBER.getLimitTypeId(),
				queryNumberLimit, Constant.LIMIT_USE_NUMBER);

		return limitRepository.save(limitQuery);
	}

	/**
	 * updateLimit 更新 已用车辆数与当日已用查询次数
	 * <p>
	 * 1、绑定车辆 -- 已用车辆数+1 (查违章时，当日已用查询次数+1)；
	 * <p>
	 * 2、解绑车辆 -- 已用车辆数-1；
	 * <p>
	 * 3、查询违章 -- 当日已用查询次数 +1 ；
	 * <p>
	 * 4、车辆列表 -- 当日已用查询次数 +1；
	 * 
	 * @param cSideUserId
	 * @param operTypeEnum
	 *            操作类型
	 * @return
	 */
	private Flux<Limit> updateLimit(String cSideUserId, OperTypeEnum operTypeEnum) {

		// 判定当日是否有查询限额记录,没有的话需要新建
		limitRepository.findByCSideUserIdAndIsDeleted(cSideUserId, Constant.NOT_DELETED)
				.filter(limitVO -> LimitTypeEnum.LIMIT_USER_QUERY_NUMBER.getLimitTypeId().equals(limitVO.getLimitType())
						&& DateUtil.inSameDate(limitVO.getGmtCreate(), new Date()))
				.switchIfEmpty(limit -> addLimitDay(cSideUserId).subscribe()).subscribe();

		Flux<Limit> fluxLimit = limitRepository.findByCSideUserIdAndIsDeleted(cSideUserId, Constant.NOT_DELETED)
				.flatMap(limit -> {

					// 使用数
					Integer useNum = limit.getUseNum();

					switch (operTypeEnum) {
					case BIND_CAR:

						if (LimitTypeEnum.LIMIT_USER_ENABLE_BIND_CAR_NUMBER.getLimitTypeId()
								.equals(limit.getLimitType())) {
							useNum++;
						}
//						if (LimitTypeEnum.LIMIT_USER_QUERY_NUMBER.getLimitTypeId().equals(limit.getLimitType())
//								&& DateUtil.inSameDate(limit.getGmtCreate(), new Date())) {
//							useNum++;
//						}

						break;

					case UN_BIND_CAR:
						if (LimitTypeEnum.LIMIT_USER_ENABLE_BIND_CAR_NUMBER.getLimitTypeId()
								.equals(limit.getLimitType())) {
							useNum--;
						}

						break;

					case QUERY_VIOLATION:
						if (LimitTypeEnum.LIMIT_USER_QUERY_NUMBER.getLimitTypeId().equals(limit.getLimitType())
								&& DateUtil.inSameDate(limit.getGmtCreate(), new Date())) {
							useNum++;
						}
						break;
					case CAR_LIST:
						if (LimitTypeEnum.LIMIT_USER_QUERY_NUMBER.getLimitTypeId().equals(limit.getLimitType())
								&& DateUtil.inSameDate(limit.getGmtCreate(), new Date())) {
							useNum++;
						}
						break;
					case BIND_CAR_FAIL:
						if (LimitTypeEnum.LIMIT_USER_QUERY_NUMBER.getLimitTypeId().equals(limit.getLimitType())
								&& DateUtil.inSameDate(limit.getGmtCreate(), new Date())) {
							useNum++;
						}
						break;
					default:
						break;
					}

					limit.setUseNum(useNum);
					limit.setGmtModified(new Date());

					return Flux.just(limit);
				});

		return limitRepository.saveAll(fluxLimit);
	}

	/**
	 * addLog 增加业务操作日志
	 * 
	 * @param cSideUserId
	 * @param carId
	 * @param operType
	 * @param operResult
	 * @param operContent
	 */
	public void addLog(String cSideUserId, String carId, Integer operType, String operResultCode, String operContent) {

		logRepository.save(new Logs(cSideUserId, carId, operType, operResultCode, operContent)).subscribe();
	}

}
