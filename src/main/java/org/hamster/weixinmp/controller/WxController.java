/**
 * 
 */
package org.hamster.weixinmp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.hamster.weixinmp.config.WxConfig;
import org.hamster.weixinmp.constant.WxMsgEventType;
import org.hamster.weixinmp.dao.entity.auth.WxAuth;
import org.hamster.weixinmp.dao.entity.base.WxBaseMsgEntity;
import org.hamster.weixinmp.dao.entity.base.WxBaseRespEntity;
import org.hamster.weixinmp.dao.entity.msg.WxMsgEventEntity;
import org.hamster.weixinmp.dao.entity.msg.WxMsgTextEntity;
import org.hamster.weixinmp.dao.entity.user.WxUserEntity;
import org.hamster.weixinmp.dao.repository.auth.WxAuthDao;
import org.hamster.weixinmp.dao.repository.msg.WxMsgTextDao;
import org.hamster.weixinmp.dao.repository.user.WxUserDao;
import org.hamster.weixinmp.exception.WxException;
import org.hamster.weixinmp.service.WxAuthService;
import org.hamster.weixinmp.service.WxMessageService;
import org.hamster.weixinmp.service.WxStorageService;
import org.hamster.weixinmp.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author grossopaforever@gmail.com
 * @version Jul 28, 2013
 * 
 */
@Controller
@RequestMapping("/wx")
public class WxController {
	private static final Logger log = Logger.getLogger(WxController.class);

	@Autowired
	private WxConfig config;
	@Autowired
	private WxAuthDao authDao;
	@Autowired
	private WxUserDao userDao;
	@Autowired
	private WxUserService userService;
	@Autowired
	private WxAuthService authService;
	@Autowired
	private WxMessageService messageService;
	@Autowired
	private WxMsgTextDao messageTextDao;
	@Autowired
	private WxStorageService storageService;

	private String accessToken;

	@RequestMapping(method = { RequestMethod.GET, })
	public @ResponseBody
	String authGet(@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			@RequestParam("echostr") String echostr) throws WxException {
		if (authService.validateAuth(signature, timestamp, nonce, echostr)) {
			log.info("received authentication message from Weixin Server.");
			return echostr;
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, produces = { "text/plain;charset=UTF-8" })
	public @ResponseBody
	String post(@RequestBody String requestBody) throws DocumentException,
			WxException {
		WxBaseMsgEntity msg = messageService.parseXML(requestBody);
		log.info("received " + msg.getMsgType() + " message.");

		WxBaseRespEntity resp = null;

		if (msg instanceof WxMsgEventEntity
				&& WxMsgEventType.SUBSCRIBE
						.equalsIgnoreCase(((WxMsgEventEntity) msg).getEvent())) {
			WxUserEntity userEntity = userDao.findByOpenId(msg
					.getFromUserName());
			if (userEntity == null) {
				if (accessToken == null) {
					WxAuth auth = authService.getAccessToken(config.getAppid(),
							config.getAppsecret());
					accessToken = auth.getAccessToken();
				}
				userEntity = userService.remoteUserInfo(accessToken,
						msg.getFromUserName());
				userDao.save(userEntity);
			}
		}

		resp = messageService.handleMessage(msg);

		return messageService.parseRespXML(resp).asXML();
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody
	WxUserEntity getUserById(@PathVariable("id") Long id) {
		return userDao.findOne(id);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody
	Map<String, List<WxUserEntity>> getUsers() {
		Map<String, List<WxUserEntity>> userMap = new HashMap<String, List<WxUserEntity>>();
		List<WxUserEntity> users = new ArrayList<WxUserEntity>();
		for (WxUserEntity user : userDao.findAll()) {
			users.add(user);
		}
		userMap.put("users", users);
		return userMap;
	}

	@RequestMapping(value = "/messages", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody
	Map<String, List<WxMsgTextEntity>> getMessages(@RequestParam String userId,
			@RequestParam String agentId) {
		Map<String, List<WxMsgTextEntity>> msgMap = new HashMap<String, List<WxMsgTextEntity>>();
		List<WxMsgTextEntity> msgs = new ArrayList<WxMsgTextEntity>();
		for (WxMsgTextEntity msg : messageTextDao.findByOpenId(userId, agentId)) {
			msgs.add(msg);
		}
		msgMap.put("messages", msgs);
		return msgMap;
	}

}
