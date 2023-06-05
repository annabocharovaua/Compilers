package token;

public abstract class StateProcess {
    protected int whitespacesCount;
    abstract public LexerState next(String ch);
    public int getWhitespacesCount(){
        return whitespacesCount;
    }
    public void closeState() {
        whitespacesCount = 0;
    }


}
