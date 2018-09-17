package com.hookmessagefromhelloworld;

import android.util.Log;
import android.view.View;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookHelloWorldIntentMes implements IXposedHookLoadPackage {

    //此项目没有入口，直接编译apk，然后安装
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {

        if("com.helloworld".equals(lpparam.packageName)){
            Class <?> clazz = null;
            try{
                clazz = lpparam.classLoader.loadClass("com.helloworld.MainActivity");
                Log.i("jw", "load class:" + clazz);

            }catch (Exception e){
                Log.i("jw", "load class err:" + Log.getStackTraceString(e));
            }
        }

        XposedHelpers.findAndHookMethod("com.helloworld.MainActivity", lpparam.classLoader, "sendMessage",
                View.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            //param.getResult()获取方法的返回值
                            //param.args[i]可以获取被hook方法的参数
                            Log.i("jw", "hook success" + param.getResult());
                        }catch (Exception e){
                            Log.i("jw", "hook err:" + Log.getStackTraceString(e));
                        }
                    }
                });


    }
}