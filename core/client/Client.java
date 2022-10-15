package prr.core.client;

import java.util.ArrayList;
import java.util.Collection;
import prr.core.terminal.Terminal;

public class Client {
    private String _key; 
    private String _name; 
    private int _taxNumber; 
    private ClientLevel _level;
    private boolean _receiveNotification;
    private Collection<Terminal> _associatedTerminals;
    private double _payments;
    private double _debts;


    public Client(String _key, String _name, int _taxNumber, ClientLevel _level, boolean _receiveNotification) {
            this._key = _key;
            this._name = _name;
            this._taxNumber = _taxNumber;
            this._level = _level;
            this._receiveNotification = _receiveNotification;
            this._associatedTerminals = new ArrayList<>();
    }

    @Override
    public String toString() {
        String baseClienString = "Client|"+_key + "|" + _name + 
            "|" + _taxNumber + "|" + _level + "|";

        if(_receiveNotification){
            baseClienString += "YES|";
        } else {
            baseClienString += "NO|";
        }
        if(_payments == 0){
            baseClienString += "0|";
        }
        if(_debts == 0 ){
            baseClienString += "0|";
        }
        return baseClienString;
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

    public String get_key() {
        return _key;
    }


}