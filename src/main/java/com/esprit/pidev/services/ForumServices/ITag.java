package com.esprit.pidev.services.ForumServices;

import com.esprit.pidev.entities.Forum.Tag;

public interface ITag {
    Tag addTag(Tag tag);
    Tag retrieveTagById(Long id);
}