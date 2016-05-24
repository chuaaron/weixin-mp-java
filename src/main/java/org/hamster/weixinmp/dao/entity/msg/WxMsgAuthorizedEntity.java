/**
 * 
 */
package org.hamster.weixinmp.dao.entity.msg;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hamster.weixinmp.config.WxConfig;
import org.hamster.weixinmp.constant.WxMsgType;
import org.hamster.weixinmp.dao.entity.base.WxBaseMsgEntity;
import org.hamster.weixinmp.dao.entity.base.WxBaseMsgInfoEntity;

/**
 * @author grossopaforever@gmail.com
 * @version Jul 28, 2013
 * 
 * 
 */
@Entity
@Table(name = WxConfig.TABLE_PREFIX + "msg_authorized")
@DiscriminatorValue(WxMsgType.AUTHORIZED)
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WxMsgAuthorizedEntity extends WxBaseMsgInfoEntity {
	@Column(name = "authorizer_appid", length = WxConfig.COL_LEN_CONTENT, nullable = false)
	private String authorizerAppid;
	@Column(name = "authorization_code", length = WxConfig.COL_LEN_CONTENT, nullable = true)
	private String authorizationCode;
	@Column(name = "authorization_code_expiretime", length = WxConfig.COL_LEN_CONTENT, nullable = true)
	private String authorizationCodeExpiredTime;	
}
