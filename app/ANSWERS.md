iterative way -> //6346 ms
ratings = loadRatingsSequentially(ids)

with coroutines ->
//460 ms
ratings = loadRatingsInParallel(ids)