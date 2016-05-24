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
@Table(name = WxConfig.TABLE_PREFIX + "msg_verifyticket")
@DiscriminatorValue(WxMsgType.COMPONENTVERIFYTICKET)
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WxMsgComponentVerifyTicketEntity extends WxBaseMsgInfoEntity {
	@Column(name = "component_verify_ticket", length = WxConfig.COL_LEN_CONTENT, nullable = false)
	private String componentVerifyTicket;
}
