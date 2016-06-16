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
@Table(name = "wx_authorizer_info")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxAuthorizerInfo extends WxBaseEntity{
	@SerializedName("nick_name")
	@Column(name = "nick_name", length = 200, nullable = false)
	private String nickname;
	@SerializedName("head_img")
	@Column(name = "head_img", length = 200, nullable = false)
	private String headImg;
	@SerializedName("user_name")
	@Column(name = "user_name", length = 200, nullable = false)
	private String username;
	@SerializedName("alias")
	@Column(name = "alias", length = 200, nullable = false)
	private String alias;	
	@SerializedName("qrcode_url")
	@Column(name = "qrcode_url", length = 200, nullable = false)
	private String qrcodeUrl;		
	@SerializedName("service_type_info")
	private WxAuthorizationServiceTypeInfo serviceTypeInfo;
	@SerializedName("verify_type_info")
	private WxAuthorizationVerifyTypeInfo verifyTypeInfo;		
	@SerializedName("business_info")
	private WxAuthorizationBusinessInfo businessInfo;			
}

