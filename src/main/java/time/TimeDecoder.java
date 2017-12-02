package time;

import PoJo.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder{ // (1)
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }

        out.add(new UnixTime(in.readUnsignedInt())); // (4)
    }
//    1.ByteToMessageDecoder是ChannelInboundHandler的一个实现，它使得处理碎片问题变得容易。
//    2.ByteToMessageDecoder在接收到新数据时，使用内部维护的累积缓冲区调用decode()方法。
//    3.decode()可以决定在累积缓冲区中没有足够数据的情况下不添加任何东西。 当接收到更多数据时，ByteToMessageDecoder将再次调用decode()。
//    4.如果decode()将对象添加到out，则意味着解码器成功地解码了消息。 ByteToMessageDecoder将丢弃累积缓冲区的读取部分。要记住，不需要
//    解码多个消息。 ByteToMessageDecoder将继续调用decode()方法，直到它没有再有任何东西添加。
}
