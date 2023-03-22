package com.api.sassila.customRepo;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class CustomRepository {
    public static void deleteMariageRelation(String key) {
        if (key!=null) {
            Connection cnx = SConnection.getInstance();
            try {
                PreparedStatement pstmt=cnx.prepareStatement("update femme set epoux_key_ = NULL where femme.epoux_key_ = ?");
                pstmt.setString(1, key);

                if(pstmt.executeUpdate()>=0)
                    log.info("Deletion done.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            log.warn("Key is null!!!");
        }
    }

    public static void deleteChildrenRelation(String key) {
        if (key!=null) {
            Connection cnx = SConnection.getInstance();
            try {
                PreparedStatement pstmt=cnx.prepareStatement("Delete from individu_enfants where individu_key_ = ? or enfants_key_ = ?");
                pstmt.setString(1, key);
                pstmt.setString(2, key);
                    log.info("Deletion done.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            log.warn("Key is null!!!");
        }
    }
}
