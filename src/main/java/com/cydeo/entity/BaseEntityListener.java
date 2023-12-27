package com.cydeo.entity;

import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityListener extends AuditingEntityListener {

    private UserRepository userRepository;

    @PrePersist
    private void onPrePersist(BaseEntity baseEntity){
        //currently logged in person
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        baseEntity.setInsertDateTime(LocalDateTime.now());
        baseEntity.setLastUpdateDateTime(LocalDateTime.now());

        if(authentication != null && !authentication.getName().equals("anonymousUser")){
            Object principal = authentication.getPrincipal();
            baseEntity.setInsertUserId(((UserPrincipal) principal).getId());
            baseEntity.setLastUpdateUserId( ((UserPrincipal) principal).getId());
        }

//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        User loggedUser = userRepository.findByUserNameAndIsDeleted(username, false);
//
//
//        if(authentication != null && !authentication.getName().equals("anonymousUser")){
//            baseEntity.setInsertUserId(loggedUser.getId());
//            baseEntity.setLastUpdateUserId( loggedUser.getId());
//        }
    }

    @PreUpdate
    private void onPreUpdate(BaseEntity baseEntity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.setLastUpdateDateTime(LocalDateTime.now());

        if(authentication != null && !authentication.getName().equals("anonymousUser")){
            Object principal = authentication.getPrincipal();
            baseEntity.setLastUpdateUserId( ((UserPrincipal) principal).getId());
        }
    }

}
