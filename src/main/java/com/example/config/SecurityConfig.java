package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()
        	.antMatchers("/loginForm").permitAll() // /loginFormは、全ユーザからのアクセスを許可
        	.anyRequest().authenticated(); // 許可した項目以外は、認証を求める
		
		//ログインの設定
		http.formLogin()
			.loginProcessingUrl("/login")        //ログイン処理のパス
			.loginPage("/loginForm")             //ログインページの指定
			.usernameParameter("email")			 //ログインページのメールアドレス
			.passwordParameter("password")		 //ログインページのパスワード
			.defaultSuccessUrl("/library", true) //ログイン成功時のパス
			.failureUrl("/loginForm?error");	 //ログイン失敗時のパス
		
		//ログアウトの設定
		http.logout()
			.logoutUrl("/logout")				//ログアウト処理のパス
			.logoutSuccessUrl("/loginForm");	//ログアウト成功時のパス
	}
	 // ハッシュアルゴリズムの定義
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    // WebSecurity型の引数を持ったconfigure()を追加します
    public void configure(WebSecurity web) throws Exception {
        /** 以下のファイルパス配下のディレクトリ、ファイルすべてを認証・認可の対象から除外します
            src/main/resources/static/css/
            src/main/resources/static/js/
        */
        web.ignoring().antMatchers("/css/**", "/js/**");
    }
}
