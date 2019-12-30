我们知道，在一个应用中，如果cpu计算的时间为Tcpu，io操作的时间为Tio，系统的cpu核数为Ncpu，线程个数为Nthread， 那么理论上线程个数满足Nthread = (1+Tio/Tcpu)*Ncpu，应用的性能达到最优

