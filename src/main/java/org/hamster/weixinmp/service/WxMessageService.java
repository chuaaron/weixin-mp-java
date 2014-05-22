/**
 * 
 */
package org.hamster.weixinmp.service;

import static org.hamster.weixinmp.util.WxUtil.getAccessTokenParams;
import static org.hamster.weixinmp.util.WxUtil.sendRequest;
import static org.hamster.weixinmp.util.WxUtil.toJsonStringEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hamster.weixinmp.config.WxConfig;
import org.hamster.weixinmp.constant.WxMsgRespType;
import org.hamster.weixinmp.constant.WxMsgRespTypeEnum;
import org.hamster.weixinmp.constant.WxMsgTypeEnum;
import org.hamster.weixinmp.controller.util.WxXmlUtil;
import org.hamster.weixinmp.dao.entity.base.WxBaseMsgEntity;
import org.hamster.weixinmp.dao.entity.base.WxBaseRespEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespImageEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespMusicEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespPicDescEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespTextEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespVideoEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespVoiceEntity;
import org.hamster.weixinmp.exception.WxException;
import org.hamster.weixinmp.model.WxRespCode;
import org.hamster.weixinmp.service.handler.WxMessageHandlerIfc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * @author grossopaforever@gmail.com
 * @version Dec 30, 2013
 * 
 */
@Service
public class WxMessageService {
	@Autowired
	WxConfig config;

	@Autowired(required = false)
	List<WxMessageHandlerIfc> handlers;

	@Autowired(required = false)
	private WxStorageService wxStorageService;

	public WxBaseMsgEntity parseXML(String xml) throws DocumentException,
			WxException {
		Element ele = DocumentHelper.parseText(xml).getRootElement();
		String msgType = null;
		if ((msgType = ele.elementText("MsgType")) == null) {
			throw new WxException("cannot find MsgType Node!\n" + xml);
		}
		WxMsgTypeEnum msgTypeEnum = WxMsgTypeEnum.inst(msgType);
		switch (msgTypeEnum) {
		case EVENT:
			// wxStorageService.saveMsgEvent(ele);
			return WxXmlUtil.getMsgEvent(ele);
		case IMAGE:
			// wxStorageService.saveMsgImg(ele);
			return WxXmlUtil.getMsgImage(ele);
		case LINK:
			// wxStorageService.saveMsgLink(ele);
			return WxXmlUtil.getMsgLink(ele);
		case LOCATION:
			// wxStorageService.saveMsgLoc(ele);
			return WxXmlUtil.getMsgLoc(ele);
		case TEXT:
			// wxStorageService.saveMsgText(ele);
			return WxXmlUtil.getMsgText(ele);
		case VIDEO:
			return WxXmlUtil.getMsgVideo(ele);
		case VOICE:
			return WxXmlUtil.getMsgVoice(ele);
		default:
			// never happens
			break;
		}
		return null;
	}

	public WxBaseRespEntity handleMessage(WxBaseMsgEntity msg) {
		List<WxMessageHandlerIfc> handlerList = new ArrayList<WxMessageHandlerIfc>();
		if (handlers != null) {
			handlerList.addAll(handlers);
		}
		Collections.sort(handlerList, new WxMessageHandlerComparator());

		Map<String, Object> context = new HashMap<String, Object>();
		WxBaseRespEntity result = null;
		for (WxMessageHandlerIfc handler : handlerList) {
			result = handler.handle(msg, context);
		}

		if (result == null) {
			result = defaultResult(msg.getToUserName(), msg.getFromUserName());
		}
		return result;
	}

	public Element parseRespXML(WxBaseRespEntity resp) throws DocumentException {
		WxMsgRespTypeEnum type = WxMsgRespTypeEnum.inst(resp.getMsgType());
		switch (type) {
		case IMAGE:
			return WxXmlUtil.getRespImage((WxRespImageEntity) resp);
		case MUSIC:
			return WxXmlUtil.getRespMusic((WxRespMusicEntity) resp,
					((WxRespMusicEntity) resp).getThumb());
		case NEWS:
			return WxXmlUtil.getRespPicDesc((WxRespPicDescEntity) resp);
		case TEXT:
			return WxXmlUtil.getRespTextXML((WxRespTextEntity) resp);
		case VIDEO:
			return WxXmlUtil.getRespVideo((WxRespVideoEntity) resp);
		case VOICE:
			return WxXmlUtil.getRespVoice((WxRespVoiceEntity) resp);
		default:
			break;
		}
		return null;
	}

	protected WxRespTextEntity defaultResult(String fromUserName,
			String toUserName) {
		WxRespTextEntity result = new WxRespTextEntity();
		result.setContent("hello, received your message. 您好,您的消息已收到.");
		result.setCreatedDate(new Date());
		result.setCreateTime(new Date().getTime() / 1000);
		result.setFromUserName(fromUserName);
		result.setMsgType(WxMsgRespType.TEXT);
		result.setToUserName(toUserName);
		return result;
	}

	public WxRespCode sendMessage(String accessToken, String toUserName,
			String content) throws WxException {
		Map<String, Object> requestJson = new HashMap<String, Object>();
		requestJson.put("touser", toUserName);
		requestJson.put("msgtype", "text");
		Map<String, Object> textJson = new HashMap<String, Object>();
		textJson.put("content", content);
		requestJson.put("text", textJson);

		WxRespCode result = sendRequest(config.getCustomSendUrl(),
				HttpMethod.POST, getAccessTokenParams(accessToken),
				toJsonStringEntity(requestJson), WxRespCode.class);

		return result;
	}

}

class WxMessageHandlerComparator implements Comparator<WxMessageHandlerIfc> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(WxMessageHandlerIfc o1, WxMessageHandlerIfc o2) {
		if (o1.priority() > o2.priority()) {
			return -1;
		} else if (o1.priority() < o2.priority()) {
			return 1;
		} else {
			return 0;
		}
	}

}
