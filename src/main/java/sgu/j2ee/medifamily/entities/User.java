package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.Builder.Default;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class User implements UserDetails {
	public enum Role {
		ROLE_USER, ROLE_DOCTOR, ROLE_ADMIN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String password;

	@Column(unique = true)

	@Email(message = "Email không hợp lệ")
	private String email;

	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
	@Default
	private Boolean isActive = true;
	private LocalDateTime lastLogin;
	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
	private Profile profile;

	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Doctor doctor;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	@ColumnDefault("'ROLE_USER'")
	private Role role = Role.ROLE_USER;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(() -> this.role.name());
	}

	@Override
	public String getUsername() {
		return this.id.toString();
	}
}
