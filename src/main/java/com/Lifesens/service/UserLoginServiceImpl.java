package com.Lifesens.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Lifesens.entity.UserInfo;
import com.Lifesens.entity.UserLogin;
import com.Lifesens.repository.UserInfoRepository;
import com.Lifesens.repository.UserLoginRepository;

/**
 * ユーザーログイン関連のサービス実装クラス
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserLoginRepository userLoginRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザー新規登録（ログイン情報 + 会員情報の保存）
	 */
	@Override
	@Transactional
	public UserLogin register(UserLogin userLogin, UserInfo userInfo) {
		// ① ユーザー基本情報を保存
		userInfo.setCreatedAt(LocalDateTime.now());
		userInfo.setUpdatedAt(LocalDateTime.now());
		UserInfo savedUserInfo = userInfoRepository.save(userInfo);

		// ② ログイン情報を保存（userId を関連付ける）
		userLogin.setUserId(savedUserInfo.getUserId());
		userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword())); // パスワードを暗号化
		userLogin.setIsActive(true);
		userLogin.setCreatedAt(LocalDateTime.now());
		userLogin.setUpdatedAt(LocalDateTime.now());

		return userLoginRepository.save(userLogin);
	}

	/**
	 * ユーザー名でログイン情報を取得
	 */
	@Override
	public Optional<UserLogin> findByUsername(String username) {
		return userLoginRepository.findByUsername(username);
	}

	/**
	 * ユーザー名でログイン情報を削除
	 */
	@Override
	public void deleteByUsername(String username) {
		Optional<UserLogin> user = userLoginRepository.findByUsername(username);
		user.ifPresent(userLoginRepository::delete);
	}

	/**
	 * パスワード照合（暗号化されたパスワードとの比較）
	 */
	@Override
	public boolean checkPassword(UserLogin user, String password) {
		return passwordEncoder.matches(password, user.getPassword());
	}

	/**
	 * パスワードの更新
	 */
	@Override
	@Transactional
	public void updatePassword(UserLogin userLogin) {
		userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword())); // 新しいパスワードを暗号化して保存
		userLogin.setUpdatedAt(LocalDateTime.now());
		userLoginRepository.save(userLogin);
	}
}
