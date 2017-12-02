package time;

import PoJo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
//        final ByteBuf time = ctx.alloc().buffer(4); // (2)
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//
//        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
//                ctx.close();
//            }
//        }); // (4)
        ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

//    1.当建立连接并准备好生成流量时，将调用channelActive()方法。现在在这个方法中编写一个32位的整数来表示当前的时间。
//    2.要发送新消息，需要分配一个包含消息的新缓冲区。我们要写入一个32位整数，因此需要一个ByteBuf，其容量至少为4个字节。
//            通过ChannelHandlerContext.alloc()获取当前的ByteBufAllocator并分配一个新的缓冲区。
//    3.但是，在NIO中发送消息之前，我们是否曾调用java.nio.ByteBuffer.flip()？ ByteBuf没有这样的方法，它只有两个指针; 一个
//            用于读取操作，另一个用于写入操作。 当您向ByteBuf写入内容时，写入索引会增加，而读取器索引不会更改。读取器索
//            引和写入器索引分别表示消息的开始和结束位置。相比之下，NIO缓冲区不提供一个干净的方式来确定消息内容开始和结束，
//            而不用调用flip方法。当您忘记翻转缓冲区时，就将会遇到麻烦，因为不会发送任何或发送不正确的数据。但是这样的错误
//            不会发生在Netty中，因为不同的操作类型我们有不同的指针。另一点要注意的是ChannelHandlerContext.write()(和
//            writeAndFlush())方法返回一个ChannelFuture。 ChannelFuture表示尚未发生的I/O操作。这意味着，任何请求的操作可能
//            尚未执行，因为所有操作在Netty中是异步的。 例如，以下代码可能会在发送消息之前关闭连接：
//
//            Channel ch = ...;
//            ch.writeAndFlush(message);
//            ch.close();
//            因此，需要在ChannelFuture完成后调用close()方法，该方法由write()方法返回，并在写入操作完成时通知其监听器。
//            请注意，close()也可能不会立即关闭连接，并返回一个ChannelFuture。
//    4.当写请求完成时，我们如何得到通知？ 这就像向返回的ChannelFuture添加ChannelFutureListener一样简单。 在这里，我们创
//            建了一个新的匿名ChannelFutureListener，当操作完成时关闭Channel。
//            或者，可以使用预定义的侦听器来简化代码f.addListener(ChannelFutureListener.CLOSE);
}
