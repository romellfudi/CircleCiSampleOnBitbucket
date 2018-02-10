package fudi.freddy.circlecisample.callback;

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2/10/18
 */

public class Module {

    Module instance = null;

    Module getInstance(){
        if (instance==null)
            instance = new Module();
        return instance;
    }

    void service(int value, Callback callback){
        callback.returnData(new Data(value));
    }
}
