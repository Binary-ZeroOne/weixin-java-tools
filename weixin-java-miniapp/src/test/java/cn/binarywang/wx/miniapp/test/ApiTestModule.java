package cn.binarywang.wx.miniapp.test;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import com.google.inject.Binder;
import com.google.inject.Module;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class ApiTestModule implements Module {

  @Override
  public void configure(Binder binder) {
    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("test-config.xml")) {
      TestConfig config = TestConfig.fromXml(inputStream);
      config.setAccessTokenLock(new ReentrantLock());

      WxMaService wxService = new cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl();
      wxService.setWxMaConfig(config);

      binder.bind(WxMaService.class).toInstance(wxService);
      binder.bind(WxMaConfig.class).toInstance(config);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
