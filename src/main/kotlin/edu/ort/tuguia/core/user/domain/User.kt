package edu.ort.tuguia.core.user.domain

import com.fasterxml.jackson.annotation.JsonInclude
import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.category.domain.Category
import edu.ort.tuguia.core.shared.Reviews
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users")
class User(
    username: String = "",
    firstName: String = "",
    lastName: String = "",
    email: String = "",
    password: String = "",
    role: UserRole? = null,
    guideIdentification: String? = null
) {
    @Id
    @NotBlank(message = "El username es obligatorio")
    var username: String

    @NotBlank(message = "El nombre es obligatorio")
    var firstName: String

    @NotBlank(message = "El apellido es obligatorio")
    var lastName: String

    @Email(message = "Se debe ingresar un email valido")
    var email: String

    @NotBlank(message = "El password es obligatorio")
    private var password: String

    @Enumerated(EnumType.STRING)
    lateinit var role: UserRole

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var phones: MutableList<UserPhone>

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var guideIdentification: String? = null

    @ManyToMany
    @JoinTable(
        name = "users_categories",
        joinColumns = [JoinColumn(name = "username")],
        inverseJoinColumns = [JoinColumn(name = "category_id")])
    var favCategories: MutableList<Category>

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var photoUrl: String? = null

    @ManyToMany
    @JoinTable(
        name = "user_fav_activities",
        joinColumns = [JoinColumn(name = "username")],
        inverseJoinColumns = [JoinColumn(name = "activity_id")])
    var favActivities: MutableList<Activity>

    @Schema(readOnly = true)
    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var reviews: Reviews? = null

    init {
        this.username = username
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = BCryptPasswordEncoder().encode(password)
        if (role != null) {
            this.role = role
        }
        this.phones = mutableListOf()
        if (guideIdentification != null) {
            this.guideIdentification = guideIdentification
        }
        this.favCategories = mutableListOf()
        this.favActivities = mutableListOf()
    }

    fun checkPassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }

    fun changePassword(newPassword: String) {
        this.password = BCryptPasswordEncoder().encode(newPassword)
    }
}