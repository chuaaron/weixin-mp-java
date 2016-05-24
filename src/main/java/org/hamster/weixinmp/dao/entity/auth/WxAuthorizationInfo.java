/**
 * 
 */
package org.hamster.weixinmp.dao.entity.auth;

import java.util.List;

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
@Table(name = "wx_authorization_info")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxAuthorizationInfo extends WxBaseEntity{
	@SerializedName("authorizer_appid")
	@Column(name = "authorizer_appid", length = 200, nullable = false)
	private String authorizerAppid;
	@SerializedName("authorizer_access_token")
	@Column(name = "authorizer_access_token", length = 200, nullable = false)
	private String authorizerAccessToken;
	@SerializedName("expires_in")
	@Column(name = "expires_in", nullable = false)
	private Long expiresIn;	
	@SerializedName("authorizer_refresh_token")
	@Column(name = "authorizer_refresh_token", length = 200, nullable = false)
	private String authorizerRefreshToken;		
	@SerializedName("func_info")
	private List<WxAuthorizationFuncInfo> funcInfo;
	
}

