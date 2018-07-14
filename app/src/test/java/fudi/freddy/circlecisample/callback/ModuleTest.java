package fudi.freddy.circlecisample.callback;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2/10/18
 */
@RunWith(PowerMockRunner.class)
public class ModuleTest {

    @Mock
    Callback callback;

    @Captor
    private ArgumentCaptor<Data> data;

    @InjectMocks
    Module module = new Module();


    @Test
    public void serviceModule() throws Exception {
        int v = 69;
        module.service(v,callback);
        verify(callback).returnData(data.capture());
        Data dataResponse = data.getValue();
        assertThat(dataResponse, is(notNullValue()));
        assertThat(dataResponse.getValue(), is(69));

    }

}