Animation = m = K = R = O = Y = G = A = B = P = M = None
from math import sqrt, floor

# The four colors used in
# the animation are black,
# red, yellow, and blue
colors = [K, R, Y, B]

# An index for each color
color_range = range(len(colors))

# Determines the number of
# balls in the animation
# and the radius of each.
# The number of colors
# must be greater than the
# number of balls
ball_radii = [1.5, 1.3, 1.1]

# An index for each ball
ball_range = range(len(ball_radii))

# Total animation time in
# milliseconds
duration = 2000

# The time that each ball
# spends moving upwards
up_time = duration/2

# Time difference between
# the ball paths
delay = up_time/2

# Each ball moves the full
# width of 8 pixels in one
# loop of the animation
x_velocity = 8/duration

# Each ball travels 5
# pixels as it is moving
# upwards
y_acceleration = 5/(up_time*up_time)

# The x coordinate of the
# center of the lead ball
# at the given time
def lead_x(time):
 return (time*x_velocity)%8

# The y coordinate of the
# center of the lead ball
# at the given time
def lead_y(time):
 if time > up_time:
  time -= duration
 return y_acceleration*time*time+2

# Loops through the balls
# and determines if a
# given point is inside a
# ball, then the index of
# that ball's color in the
# colors list is returned.
# The first color is
# black, so if the point
# is not in any ball then
# index 0 is returned
def point_color_index(x, y, time):
 for i in ball_range:
  ball_x = lead_x(time)
  ball_y = lead_y(time)
  dx = x-ball_x
  if dx < -4:
   dx += 8
  elif dx > 4:
   dx -= 8
  dy = y-ball_y
  dist_to_ball = sqrt(dx*dx+dy*dy)
  if dist_to_ball < ball_radii[i]:
   return i+1
  time -= delay
 return 0

# Number of rows of points
# in one pixel, same as
# the number of columns
point_rows = 10

# The distance between two
# adjacent points if the
# width of a pixel is 1
point_spacing = 1/point_rows

# The shortest distance
# from the edge of a pixel
# to one of the points
offset = point_spacing/2

# Row and column indices
# for points
point_range = range(point_rows)

# Multiplying by this
# number has the effect of
# dividing by the number
# of points in a pixel
scale = 1/(point_rows*point_rows)

# Indices of (R, G, B)
rgb_range = range(3)

# Calculates the color of
# a pixel by taking the
# average color of evenly
# spaced points inside the
# pixel. The color of each
# point is determined by
# the point_color_index
# function
def pixel_color(row, col, time):
 tl_x = col+offset
 tl_y = row+offset
 color_counts = [0]*len(colors)
 for r in point_range:
  for c in point_range:
   x = tl_x+c*point_spacing
   y = tl_y+r*point_spacing
   i = point_color_index(x, y, time)
   color_counts[i] += 1
 avg_color = [0, 0, 0]
 for i in rgb_range:
  for j in color_range:
   avg_color[i] += colors[j][i]*color_counts[j]
  avg_color[i] = floor(avg_color[i]*scale)
 return tuple(avg_color)

# Number of animation
# frames, maximum of 100
frames = 40

# Number of milliseconds
# per frame, minimum of 25
frame_time = max(25, floor(duration/frames))

# Row and column indices
# for pixels
pixel_range = range(8)

# Uses the pixel_color
# function to generate the
# entire animation
ani = Animation()
for t in range(frames):
 time = t*frame_time
 for r in pixel_range:
  for c in pixel_range:
   m[r][c] = pixel_color(r, c, time)
 ani.add_frame(m, frame_time)
