package prr.core;

public class Client {
    private String _key; 
    private String _name; 
    private int _taxNumber; 
    private ClientLevel _level;
    private boolean _receiveNotification;

public Client(String _key, String _name, int _taxNumber, ClientLevel _level) {
    this._key = _key;
    this._name = _name;
    this._taxNumber = _taxNumber;
    this._level = _level;
} 

}
