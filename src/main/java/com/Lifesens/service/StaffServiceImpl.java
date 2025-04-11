package com.Lifesens.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Lifesens.entity.Staff;
import com.Lifesens.repository.StaffRepository;

/**
 * スタッフ関連サービスの実装クラス
 */
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    /**
     * スタッフ情報を保存（新規・更新）
     * @param staff スタッフ情報
     * @return 保存されたスタッフ情報
     */
    @Override
    public Staff save(Staff staff) {
        // 新規作成時は作成日時を設定
        if (staff.getStaffId() == null) {
            staff.setCreatedAt(LocalDateTime.now());
        }
        // 更新日時は常に設定
        staff.setUpdatedAt(LocalDateTime.now());
        return staffRepository.save(staff);
    }

    /**
     * 全スタッフ情報を取得
     * @return スタッフのリスト
     */
    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    /**
     * IDでスタッフを検索
     * @param id スタッフID
     * @return 該当するスタッフ（存在しない場合は空）
     */
    @Override
    public Optional<Staff> findById(Integer id) {
        return staffRepository.findById(id);
    }

    /**
     * ユーザー名でスタッフを検索
     * @param username ユーザー名
     * @return 該当するスタッフ（存在しない場合は空）
     */
    @Override
    public Optional<Staff> findByUsername(String username) {
        return staffRepository.findByUsername(username);
    }

    /**
     * IDでスタッフを削除
     * @param id スタッフID
     */
    @Override
    public void deleteById(Integer id) {
        staffRepository.deleteById(id);
    }
}
