package hu.indicium.dev.payment.infrastructure.setting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, String> {
    Optional<Setting> findByKey(String key);
}
