package com.ynkj.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具类
 *
 * @author yhb
 * @version 1.0
 * @date 2012-12-26
 */
public class ThreadPoolUtil {
    // 默认线程池大小
	public static final int DEFAULT_POOL_SIZE = 10;
	// 默认一个任务的超时时间，单位为毫秒
	public static final long DEFAULT_TASK_TIMEOUT = 1000;
	// 线程池大小
	private static int poolSize = DEFAULT_POOL_SIZE;
	// 执行线程任务对象
	private static ExecutorService executorService;
	
	/**
	 * <p>构造方法</p>
	 * <p>根据给定大小创建线程池</p>
	 * @param poolSize
	 */
    public ThreadPoolUtil(int poolSize) {
    	// 调整线程池大小
        setPoolSize(poolSize);
    }
    
    /**
     * 调整线程池大小
     * 
     * @param poolSize
     */
    public static void setPoolSize(int poolSize) {
    	ThreadPoolUtil.poolSize = poolSize;
        // 根据poolSize创建新的ExecutorService
        createExecutorService();
    }
    
    /**
     * <p>关闭当前ExecutorService</p>
     * <p>随后根据poolSize创建新的ExecutorService</p>
     */
    public static void createExecutorService() {
    	// 关闭当前ExecutorService
        destoryExecutorService(DEFAULT_TASK_TIMEOUT);
        // 创建固定大小的线程池
        executorService = Executors.newFixedThreadPool(poolSize);
    }
    
    /**
     * 关闭当前ExecutorService
     * 
     * @param timeout 以毫秒为单位的超时时间
     */
    public static void destoryExecutorService(long timeout) {
    	// 进行非空、是否已关闭判断
		if (executorService != null && !executorService.isShutdown()) {
			try {
				// 请求关闭、发生超时或者当前线程中断，无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行
				executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 启动一次顺序关闭，执行以前提交的任务，但不接受新任务
			executorService.shutdown();
		}
    }
    
    /**
     * 使用线程池中的线程来执行任务
     */
    public static void execute(Runnable task) {
    	// 执行指定线程任务
        executorService.execute(task);
    }
    
    /**
     * <p>main方法</p>
     * <p>利用多线程遍历list数组，把数组中的值进行+10操作</p>
     * 
     * @param args
     */
    public static void main(String[] args) {
		/*
		 * 生成测试list
		 */
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}

		// 调整线程池大小
		ThreadPoolUtil.setPoolSize(3);
		// 创建新的ExecutorService
		ThreadPoolUtil.createExecutorService();
		// 锁存器的计数
		CountDownLatch signal = new CountDownLatch(list.size());
		
		// 开始时间
		long start = System.currentTimeMillis();
		// 遍历数组，把数组中的值进行+10
		for (int i = 0; i < list.size(); i++) {
			// 使用线程池中的线程来执行任务
			ThreadPoolUtil.execute(new Worker(list, signal, i));
		}

		try {
			// 使当前线程在锁存器倒计数至零之前一直等待
			signal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 结束时间
		long stop = System.currentTimeMillis();
		// 关闭当前ExecutorService
		ThreadPoolUtil.destoryExecutorService(ThreadPoolUtil.DEFAULT_TASK_TIMEOUT);
		System.out.println("实际执行时间: " + (stop - start) + "ms");

		/*
		 * 打印处理后的list
		 */
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
	}
}

/**
 * 把数字加10
 *
 * @author yhb
 * @version 1.0
 * @date 2012-12-26
 */
class Worker implements Runnable{
	List<Integer> list;
	CountDownLatch signal;
	AtomicInteger count;
	int i;
	
	/**
	 * 构造方法
	 *  
	 * @param list
	 * @param signal
	 * @param count
	 */
	public Worker(List<Integer> list, CountDownLatch signal,int count) {
		this.list = list;
		this.signal = signal;
		this.i = count;
	}
	
	@Override
	public void run() {
		System.out.println("第" + (i + 1) + "个线程启动：");
		// lis[i]的值+10
		list.set(i, list.get(i) + 10);
		try {
			// 随机等等时间
			Thread.sleep((long) (Math.random() * 10000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(String.format("threadID:[%s] finished!!", i));
		//  递减锁存器的计数
		signal.countDown();
	}
}