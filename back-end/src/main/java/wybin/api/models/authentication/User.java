package wybin.api.models.authentication;

import wybin.api.models.helpers.MeHelper;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {
    @Id
    private Long id;
    private String username;
    private String avatarUrl;
    private String coverUrl;
    private String countryCode;
    private Integer pp;
    private Integer rank;
    private Date lastUpdate;
    private Integer role;

    public User() {
    }

    public User(Long id, String username, String avatarUrl, String coverUrl, String countryCode, Integer pp, Integer rank, Date lastUpdate, Integer role) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.coverUrl = coverUrl;
        this.countryCode = countryCode;
        this.pp = pp;
        this.rank = rank;
        this.lastUpdate = lastUpdate;
        this.role = role;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getPp() {
        return pp;
    }

    public void setPp(Integer pp) {
        this.pp = pp;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void updateFromMeHelper(MeHelper meHelper) {
        this.id = meHelper.getId();
        this.username = meHelper.getUsername();
        this.avatarUrl = meHelper.getAvatar_url();
        this.coverUrl = meHelper.getCover_url();
        this.pp = meHelper.getPp();
        this.rank = meHelper.getRank();
        this.countryCode = meHelper.getFlag();
        this.lastUpdate = new Date();
    }
}
