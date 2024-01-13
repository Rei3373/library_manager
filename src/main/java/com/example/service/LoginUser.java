package com.example.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.entity.User;

public class LoginUser implements UserDetails{
	
	//Userオブジェクト
	public User user;
	
	//コンストラクタ
	public LoginUser(User user) {
		this.user = user;
	}
	
	//Userオブジェクトのゲッター
	public User getUser() {
		return this.user;
	}
	
	//ユーザ認証に使われるパスワードを返却
	@Override
	public String getPassword() {
		return this.user.getPassword();
	}
	
	//ユーザ認証に使われるユーザ名を返却
	@Override
	public String getUsername() {
		return this.user.getEmail();
	}
	
	//ユーザに付与された権限を返却
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return AuthorityUtils.NO_AUTHORITIES;
	}
	
	//アカウントの有効期限の状態を判定
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//アカウントのロック状態を判定
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// 資格情報の有効期限の状態を判定する
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
	
	//有効なユーザか判定
	@Override
	public boolean isEnabled() {
		return true;
	}
}
