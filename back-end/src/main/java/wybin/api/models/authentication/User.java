package wybin.api.models.authentication;

import com.fasterxml.jackson.annotation.JsonView;
import wybin.api.models.JsonViews;

import javax.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(JsonViews.ShowUser.class)
	private Long id;

	@JsonView(JsonViews.ShowUser.class)
	private String username;
	private String password;

	@Transient
	private String passwordConfirm;

	@JsonView(JsonViews.ShowUser.class)
	private boolean isAdmin;

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}
}
