package discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // 以静默方式丢弃接收的数据
        ByteBuf in = (ByteBuf) msg; // (3)
        System.out.print(in.toString(io.netty.util.CharsetUtil.US_ASCII));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // 出现异常时关闭连接。
        cause.printStackTrace();
        ctx.close();
    }
}
