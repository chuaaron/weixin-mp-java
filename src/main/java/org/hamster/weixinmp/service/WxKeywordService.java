/**
 * 
 */
package org.hamster.weixinmp.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamster.weixinmp.config.WxConfig;
import org.hamster.weixinmp.dao.entity.auth.WxAuth;
import org.hamster.weixinmp.dao.entity.auth.WxAuthRefresh;
import org.hamster.weixinmp.dao.entity.auth.WxAuthReq;
import org.hamster.weixinmp.dao.entity.auth.WxAuthorizerAuth;
import org.hamster.weixinmp.dao.entity.auth.WxQueryAuthorizerAuth;
import org.hamster.weixinmp.exception.WxException;
import org.hamster.weixinmp.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.apache.http.Consts;
import org.apache.http.entity.StringEntity;

import static org.hamster.weixinmp.util.WxUtil.getAccessTokenParams;
import static org.hamster.weixinmp.util.WxUtil.sendRequest;
import static org.hamster.weixinmp.util.WxUtil.toJsonString;

import org.hamster.weixinmp.dao.entity.auth.WxComponentAuth;
import org.hamster.weixinmp.dao.entity.auth.WxComponentPreauthCode;
import org.hamster.weixinmp.dao.entity.keywords.WxAutoReply;
import org.hamster.weixinmp.model.WxRespCode;

import com.google.gson.Gson;

/**
 * @author grossopaforever@gmail.com
 * @version Jan 1, 2014
 * 
 */
@Service
public class WxKeywordService {
	
	private static final Logger log = LoggerFactory.getLogger(WxKeywordService.class);
	
	@Autowired
	protected WxConfig config;

	public WxAutoReply getAutoReplyInfo(String accessToken)
			throws WxException {
		Map<String, String> params = getAccessTokenParams(accessToken);
		WxAutoReply wxAutoReply =  WxUtil.sendRequest(config.getKeywordsUrl(), HttpMethod.GET, params, null,
				WxAutoReply.class);
		return wxAutoReply;
	}
	
	public WxAutoReply getAutoReplyInfoJsonStr(String accessToken)
			throws WxException {
		Map<String, String> params = getAccessTokenParams(accessToken);
		String wxAutoReplyJsonStr =  WxUtil.sendRequest(config.getKeywordsUrl(), HttpMethod.GET, params, null,
				String.class);
		Gson gson = new Gson();
		WxAutoReply wxAutoReply = gson.fromJson(wxAutoReplyJsonStr, WxAutoReply.class);
		wxAutoReply.setAutoReplyJsonStr(wxAutoReplyJsonStr);
		return wxAutoReply;
	}
	
	
	
}
