package wybin.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;

public class MeHelper {
    private String id;
    private String username;
    private String cover_url;
    private Double pp;
    private Integer rank;
    private String avatar_url;
    private String flag;

    public MeHelper() {
    }

    @JsonProperty("statistics")
    private void unpackStatistics(LinkedHashMap<Object, Object> statistics) {
        pp = Double.parseDouble(statistics.get("pp").toString());
        rank = (Integer) statistics.get("pp_rank");
    }

    @JsonProperty("country")
    private void unpackCountry(LinkedHashMap<Object, Object> country) {
        flag = (String) country.get("code");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public Double getPp() {
        return pp;
    }

    public void setPp(Double pp) {
        this.pp = pp;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
