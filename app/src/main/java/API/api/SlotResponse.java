package API.api;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by jelle on 06/11/2016.
 * Deze slotresponse geeft terug wat zich in een container bevind (naam).
 * Hij geeft ook terug wat de identifier van die container is.
 * En een status/message
 * en natuurlijk de benodigde getters en setters
 */
public class SlotResponse
{
    @JsonProperty
    private int status;

    @JsonProperty
    private String message;

    @JsonProperty
    private int slotId;

    @JsonProperty
    private String drankNaam;

    @JsonProperty
    private int mlDrank;

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

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getDrankNaam() {
        return drankNaam;
    }

    public void setDrankNaam(String drankNaam) {
        this.drankNaam = drankNaam;
    }

    public int getMlDrank() {
        return mlDrank;
    }

    public void setMlDrank(int mlDrank) {
        this.mlDrank = mlDrank;
    }
}
