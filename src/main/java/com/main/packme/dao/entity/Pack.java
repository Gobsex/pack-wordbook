package com.main.packme.dao.entity;


import com.main.packme.convertors.JpaConverterJson;
import com.main.packme.dao.components.WordList;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "packs")

public class Pack{
    @Id
    @GeneratedValue
    private long id;
    @NotEmpty(message = "type can't be empty")
    private String type;
    @Size(min = 2,message = "name should be at least 2 letters")
    private String name;
    private String description;
    @NotEmpty(message = "language can't be empty")
    private String first_ln;
    @NotEmpty(message = "language can't be empty")
    private String second_ln;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;
    @Transient
    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Convert(converter = JpaConverterJson.class)
    @Column(columnDefinition = "json")
    private WordList wordList;



    public Pack(String name, String first_ln, String second_ln, WordList wordList, String type) {
        this.name = name;
        this.first_ln = first_ln;
        this.second_ln = second_ln;
        this.wordList = wordList;
        this.type = type;
    }

    public String getFirst_ln() {
        return first_ln;
    }
    public void removeWord(long id){
        wordList.remove(id);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pack that = (Pack) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setFirst_ln(String first_ln) {
        this.first_ln = first_ln;
    }

    public String getSecond_ln() {
        return second_ln;
    }

    public void setSecond_ln(String second_ln) {
        this.second_ln = second_ln;
    }

    //                              698298e3ac0ff478ba03

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWordList(WordList wordList) {
        this.wordList = wordList;
    }

    public Pack() {
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WordList getWordList() {
        if (wordList == null) {
            wordList = new WordList();
        }
        return wordList;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PackEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", first_ln='" + first_ln + '\'' +
                ", second_ln='" + second_ln + '\'' +
                ", wordList=" + wordList +
                '}';
    }
}
