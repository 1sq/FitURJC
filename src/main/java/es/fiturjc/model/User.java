package es.fiturjc.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

	public interface Basic{}
	public interface Details{}
	public interface None{}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Basic.class)
	private long id;

	@Column(unique = true)
	@JsonView(Basic.class)
	private String nickname;
	
	@JsonView(Basic.class)
	private String name;
	
	@JsonView(Basic.class)
	private String surname;
	
	@JsonView(None.class)
	private String passwordHash; // Do not see in Rest
	
	@JsonView(Details.class)
	private String imgSrc;
	@Column(unique = true)
	@JsonView(Basic.class)
	private String email;

	@JsonView(Details.class)
	private int age;
	@ElementCollection(fetch = FetchType.EAGER)
	@JsonView(Details.class)
	private List<String> roles;

	private boolean fullProfile;


    public User() {
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNickname() {
		return nickname;
	}
	
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void changePassword(String newPassword) {
		this.passwordHash = new BCryptPasswordEncoder().encode(newPassword);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    public boolean isFullProfile() {
        return fullProfile;
    }

    public void setFullProfile(boolean fullProfile) {
        this.fullProfile = fullProfile;
    }

	@Override
	public String toString() {
		return "user [id=" + id + ", name=" + name + ", surname=" + surname + ", age=" + age + " ]";

	}

	// String .. roles admin various roles names 
	public User(String name, String surname, int age, String passwordHash, String email,
			String nickname, boolean fullProfile, String... roles) {
		super();
		this.name = name;
		this.surname = surname;
		this.passwordHash = new BCryptPasswordEncoder().encode(passwordHash);
		this.email = email;
		this.age = age;
		this.nickname = nickname;
		this.roles = new ArrayList<>(Arrays.asList(roles));
		this.imgSrc = "/uploads/img/default";
		this.fullProfile = fullProfile;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id;
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
	
	public boolean isAdmin() {
		return this.getRoles().stream().anyMatch(x-> x.equals("ROLE_ADMIN"));
	}
}
