import java.nio.ByteBuffer;
import java.util.Arrays;



public class PDU {


    private int seq_Number;
    private int ack_Number;

    private int flag;

    private long checkSum;

    private byte[] data;

    public PDU(){
        this.seq_Number = 0;
        this.ack_Number = 0;
        this.flag = 0;
        this.checkSum = 0;
    }

    public PDU(PDU pdu){
        this.seq_Number = pdu.getSeq_Number();
        this.ack_Number = pdu.getAck_Number();
        this.flag = pdu.getFlag();
        this.checkSum = pdu.getCheckSum();
        this.data = pdu.getData();

    }

    public int getSeq_Number(){
        return this.seq_Number;
    }

    public int getAck_Number(){
        return this.ack_Number;
    }

    public int getFlag(){
        return this.flag;
    }

    public long getCheckSum(){ return this.checkSum; }

    public byte[] getData(){
        return this.data;
    }

    public void setSeq_Number(int seq_Number){
        this.seq_Number = seq_Number;
    }

    public void setAck_Number(int ack_Number){
        this.ack_Number = ack_Number;
    }

    public void setFlag(int flag){
        this.flag = flag;
    }

    public void setCheckSum(long checkSum){ this.checkSum = checkSum; }

    public void setData(byte[] data){
        this.data = data;
    }

    public byte[] toBytes(){
        ByteBuffer packet = ByteBuffer.allocate(20 + this.data.length);

        packet.put(ByteBuffer.allocate(4).putInt(this.seq_Number).array());
        packet.put(ByteBuffer.allocate(4).putInt(this.ack_Number).array());
        packet.put(ByteBuffer.allocate(4).putInt(this.flag).array());
        packet.put(ByteBuffer.allocate(8).putLong(this.checkSum).array());
        packet.put(this.data);

        return packet.array();
    }

    public void fromBytes(byte[] pdu, int length){
        this.setSeq_Number(ByteBuffer.wrap(Arrays.copyOfRange(pdu, 0, 4)).getInt());
        this.setAck_Number(ByteBuffer.wrap(Arrays.copyOfRange(pdu, 4, 8)).getInt());
        this.setFlag(ByteBuffer.wrap(Arrays.copyOfRange(pdu, 8, 12)).getInt());
        this.setCheckSum(ByteBuffer.wrap(Arrays.copyOfRange(pdu,12, 20)).getLong());
        this.setData(ByteBuffer.wrap(Arrays.copyOfRange(pdu, 20, length)).array());
    }

    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("SeqNumber: ").append(this.seq_Number)
                .append('\t' + "AckNumber: ").append(this.ack_Number)
                .append('\t' + "FlagNumber: ").append(this.flag)
                .append('\t' + "Checksum: ").append(this.checkSum);
                //.append('\t' + "Data: ").append(new String(this.data));


        return sb.toString();
    }

    public PDU clone(){
        return new PDU(this);
    }


}
