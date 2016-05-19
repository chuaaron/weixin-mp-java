/**
 * 
 */
package org.hamster.weixinmp.dao.entity.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hamster.weixinmp.dao.entity.base.WxBaseEntity;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * 
 * @author grossopaforever@gmail.com
 * @version Aug 3, 2013
 * 
 */
@Entity
@Table(name = "wx_component_pre_auth_code")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxComponentPreauthCode extends WxBaseEntity {
	@SerializedName("pre_auth_code")
	@Column(name = "pre_auth_code", length = 200, nullable = false)
	private String preAuthCode;
	@SerializedName("expires_in")
	@Column(name = "expires_in", nullable = false)
	private Long expiresIn;
}
