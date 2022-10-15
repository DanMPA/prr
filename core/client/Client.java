package prr.core.client;

public class Client {
    private String _key; 
    private String _name; 
    private int _taxNumber; 
    private ClientLevel _level;
    private boolean _receiveNotification;


    public Client(String _key, String _name, int _taxNumber, ClientLevel _level, boolean _receiveNotification) {
            this._key = _key;
            this._name = _name;
            this._taxNumber = _taxNumber;
            this._level = _level;
            this._receiveNotification = _receiveNotification;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_key == null) ? 0 : _key.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Client other = (Client) obj;
        if (_key == null) {
            if (other._key != null)
                return false;
        } else if (!_key.equals(other._key))
            return false;
        return true;
    }




}