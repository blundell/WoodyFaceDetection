# 1.1.1

Removes unused files like ic_launcher.png that can cause confusion & conflicts

# 1.1.0

BUG FIX - if you attempted to have two activities doing face detection you would get locking issues with the camera.
Now resolved using a stack to keep a reference to which activity wants to use the camera at what time.

# 1.0.0

Initial release
