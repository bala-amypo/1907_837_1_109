INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< com.example:demo >--------------------------
[INFO] Building demo 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.3.2:clean (default-clean) @ demo ---
[INFO] Deleting /home/coder/Workspace/demo/target
[INFO] 
[INFO] --- maven-resources-plugin:3.3.1:resources (default-resources) @ demo ---
[INFO] Copying 1 resource from src/main/resources to target/classes
[INFO] Copying 0 resource from src/main/resources to target/classes
[INFO] 
[INFO] --- maven-compiler-plugin:3.13.0:compile (default-compile) @ demo ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 39 source files with javac [debug parameters release 17] to target/classes
[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/security/CustomUserDetailsService.java:[24,17] cannot find symbol
  symbol:   method orElseThrow(()->new Us[...]mail))
  location: class com.example.demo.entity.UserProfile
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/security/CustomUserDetailsService.java:[28,20] cannot find symbol
  symbol:   class CustomUserDetails
  location: class com.example.demo.security.CustomUserDetailsService
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[12,8] com.example.demo.service.impl.UserProfileServiceImpl is not abstract and does not override abstract method findByEmail(java.lang.String) in com.example.demo.service.UserProfileService
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[28,5] method does not override or implement a method from a supertype
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[31,17] cannot find symbol
  symbol:   method orElseThrow(()->new Re[...]mail))
  location: class com.example.demo.entity.UserProfile
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[49,40] cannot find symbol
  symbol:   method getName()
  location: variable updatedProfile of type com.example.demo.entity.UserProfile
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[51,39] cannot find symbol
  symbol:   method getAge()
  location: variable updatedProfile of type com.example.demo.entity.UserProfile
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[52,42] cannot find symbol
  symbol:   method getGender()
  location: variable updatedProfile of type com.example.demo.entity.UserProfile
[INFO] 8 errors 
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  13.093 s
[INFO] Finished at: 2025-12-20T18:26:55Z
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.13.0:compile (default-compile) on project demo: Compilation failure: Compilation failure: 
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/security/CustomUserDetailsService.java:[24,17] cannot find symbol
[ERROR]   symbol:   method orElseThrow(()->new Us[...]mail))
[ERROR]   location: class com.example.demo.entity.UserProfile
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/security/CustomUserDetailsService.java:[28,20] cannot find symbol
[ERROR]   symbol:   class CustomUserDetails
[ERROR]   location: class com.example.demo.security.CustomUserDetailsService
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[12,8] com.example.demo.service.impl.UserProfileServiceImpl is not abstract and does not override abstract method findByEmail(java.lang.String) in com.example.demo.service.UserProfileService
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[28,5] method does not override or implement a method from a supertype
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[31,17] cannot find symbol
[ERROR]   symbol:   method orElseThrow(()->new Re[...]mail))
[ERROR]   location: class com.example.demo.entity.UserProfile
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[49,40] cannot find symbol
[ERROR]   symbol:   method getName()
[ERROR]   location: variable updatedProfile of type com.example.demo.entity.UserProfile
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[51,39] cannot find symbol
[ERROR]   symbol:   method getAge()
[ERROR]   location: variable updatedProfile of type com.example.demo.entity.UserProfile
[ERROR] /home/coder/Workspace/demo/src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java:[52,42] cannot find symbol
[ERROR]   symbol:   method getGender()
[ERROR]   location: variable updatedProfile of type com.example.demo.entity.UserProfile
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException