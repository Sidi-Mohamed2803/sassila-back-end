package com.api.sassila.customRepo;

import com.api.sassila.modele.Individu;
import com.api.sassila.repository.IndividuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomRepository {
    private final IndividuRepository individuRepository;

    public CustomRepository(IndividuRepository individuRepository) {
        this.individuRepository=individuRepository;
    }


    public List<Individu> findAllWithoutParent() {
        Connection cnx = SConnection.getInstance();
        List<Individu> individus = new ArrayList<>();
        try {
            PreparedStatement pstmt = cnx.prepareStatement("SELECT * FROM individu WHERE id NOT IN (SELECT enfants_id FROM individu_enfants)");
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
//                log.info(resultSet.getLong("id")+"*********************\n");
                individus.add(individuRepository.findById(resultSet.getLong("id")).get());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return individus;
    }

    public void deleteMariageRelation(Long id) {
        if (id!=null) {
            Connection cnx = SConnection.getInstance();
            try {
                PreparedStatement pstmt=cnx.prepareStatement("update femme set epoux_id = NULL where femme.epoux_id = ?");
                pstmt.setLong(1, id);

                if(pstmt.executeUpdate()>=0)
                    log.info("Deletion done.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            log.warn("Id is null!!!");
        }
    }

    public void deleteChildrenRelation(Long id) {
        if (id!=null) {
            Connection cnx = SConnection.getInstance();
            try {
                PreparedStatement pstmt=cnx.prepareStatement("Delete from individu_enfants where individu_id_ = ? or enfants_id_ = ?");
                pstmt.setLong(1, id);
                pstmt.setLong(2, id);
                    log.info("Deletion done.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            log.warn("Id is null!!!");
        }
    }
}
