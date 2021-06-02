package com.sweater.domain.dto;

import com.sweater.domain.Message;
import com.sweater.domain.User;
import com.sweater.domain.util.MessageHelper;
import lombok.Getter;
import lombok.ToString;

@Getter
//@ToString
public class MessageDto {
    private Long id;
//    @ToString.Exclude
    private String text;
//    @ToString.Exclude
    private String tag;
    private User author;
//    @ToString.Exclude
    private String filename;
    private Long likes;
    private Boolean meLiked;

    public MessageDto(Message message, Long likes, Boolean meLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", meLiked=" + meLiked +
                '}';
    }
}
