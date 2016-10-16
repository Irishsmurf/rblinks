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
    '//lib:pircbot',
    '//lib:mongo-java'
  ],
)
