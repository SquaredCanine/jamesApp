package API.api;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by jelle on 08/11/2016.
 * Deze response word teruggestuurd vanaf de server met de inhoud van een mixdrank, hoeveelheden en namen
 * Je krijgt ook een status terug en een message
 * en natuurlijk de identifier van die drank
 * En de bijbehorende getters en setters
 */
public class MixDrankResponse
{
    @JsonProperty
    private int status;

    @JsonProperty
    private String message;

    @JsonProperty
    private int mixDrankId;

    @JsonProperty
    private String mixDrankNaam;

    @JsonProperty
    private String naamDrankEen;

    @JsonProperty
    private int mlDrankEen;

    @JsonProperty
    private String naamDrankTwee;

    @JsonProperty
    private int mlDrankTwee;

    @JsonProperty
    private String naamDrankDrie;

    @JsonProperty
    private int mlDrankDrie;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMixDrankId() {
        return mixDrankId;
    }

    public void setMixDrankId(int mixDrankId) {
        this.mixDrankId = mixDrankId;
    }

    public String getMixDrankNaam() {
        return mixDrankNaam;
    }

    public void setMixDrankNaam(String mixDrankNaam) {
        this.mixDrankNaam = mixDrankNaam;
    }

    public String getNaamDrankEen() {
        return naamDrankEen;
    }

    public void setNaamDrankEen(String naamDrankEen) {
        this.naamDrankEen = naamDrankEen;
    }

    public int getMlDrankEen() {
        return mlDrankEen;
    }

    public void setMlDrankEen(int mlDrankEen) {
        this.mlDrankEen = mlDrankEen;
    }

    public String getNaamDrankTwee() {
        return naamDrankTwee;
    }

    public void setNaamDrankTwee(String naamDrankTwee) {
        this.naamDrankTwee = naamDrankTwee;
    }

    public int getMlDrankTwee() {
        return mlDrankTwee;
    }

    public void setMlDrankTwee(int mlDrankTwee) {
        this.mlDrankTwee = mlDrankTwee;
    }

    public String getNaamDrankDrie() {
        return naamDrankDrie;
    }

    public void setNaamDrankDrie(String naamDrankDrie) {
        this.naamDrankDrie = naamDrankDrie;
    }

    public int getMlDrankDrie() {
        return mlDrankDrie;
    }

    public void setMlDrankDrie(int mlDrankDrie) {
        this.mlDrankDrie = mlDrankDrie;
    }
}
