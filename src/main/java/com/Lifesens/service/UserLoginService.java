package com.Lifesens.service;

import java.util.Optional;

import com.Lifesens.entity.UserInfo;
import com.Lifesens.entity.UserLogin;

/**
 * ユーザーログイン関連のサービスインターフェース
 */
public interface UserLoginService {

    /**
     * 新規ユーザー登録（ログイン情報 + ユーザー情報）
     * @param userLogin ログイン情報
     * @param userInfo ユーザー情報
     * @return 登録されたユーザー
     */
    UserLogin register(UserLogin userLogin, UserInfo userInfo);

    /**
     * ユーザー名からログイン情報を取得
     * @param username ユーザー名
     * @return 該当するユーザー（存在しない場合は空）
     */
    Optional<UserLogin> findByUsername(String username);

    /**
     * ユーザー名に基づいてログイン情報を削除
     * @param username ユーザー名
     */
    void deleteByUsername(String username);

    /**
     * パスワードチェック
     * @param userLogin 登録済みユーザー情報
     * @param rawPassword 入力された平文パスワード
     * @return 正しければ true、間違っていれば false
     */
    boolean checkPassword(UserLogin userLogin, String rawPassword);

    /**
     * パスワードを更新する
     * @param userLogin 対象ユーザー
     */
    void updatePassword(UserLogin userLogin);
}
