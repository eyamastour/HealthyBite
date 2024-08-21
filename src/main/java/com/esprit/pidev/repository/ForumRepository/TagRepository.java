package com.esprit.pidev.repository.ForumRepository;

import com.esprit.pidev.entities.Forum.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
}
