package com.warm.demo.login;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.widget.Toast;

import com.warm.demo.login.rx.RxLogin;
import com.warm.router.Interceptor;
import com.warm.router.Request;
import com.warm.router.annotations.RouteInterceptor;

import io.reactivex.functions.Consumer;

@RouteInterceptor(name = "LoginInterceptor")
public class LoginInterceptor implements Interceptor {
    Object result;

    @Override
    public void intercept(final Chain chain) {
        final Request request = chain.request();

        RxLogin rxLogin = null;
        Fragment fragment = chain.getFragment();
        final Context context = chain.getContext();

        if (fragment != null) {
            rxLogin = new RxLogin(fragment);
        } else if (context instanceof FragmentActivity) {
            rxLogin = new RxLogin((FragmentActivity) context);
        }
        if (rxLogin != null) {
            rxLogin.login()
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                chain.proceed(request);
                            } else {
                                Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });

        }
    }
}
