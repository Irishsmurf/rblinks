java_binary(
  name='rblinks',
  srcs=[
    'com/redbrick/devchat/rblinks/RbLinks.java',
  ],
  main_class = 'com.redbrick.devchat.rblinks.RbLinks',
  deps=[
    ':rblinkbot'
  ]
)

java_library(
  name='rblinkbot',
  srcs = [
    'com/redbrick/devchat/rblinks/RbLinkbot.java',
  ],
  deps = [
    '@pircbot//jar',
    '@org_mongodb_mongodb_driver//jar',
    '@org_mongodb_mongodb_java_driver//jar',
    '@org_mongodb_mongodb_driver_async//jar',
    '@org_mongodb_mongodb_driver_core//jar',
  ],
)
