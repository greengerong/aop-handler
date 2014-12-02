aop-handler
===========

Use java Jdk 7.0 Proxy to do the AOP work for your java bean. This is use for your project which without spring or

another IOC/AOP framework(like OSGI/ODL).

You can use like that:

    final StudentDao proxyInstance = AopUtils.proxy(studentDao, exception());


Task:

- [x] Exception handler.
- [ ] Authentication
- [ ] Feature toggle.