import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const OnlinePingApp());
}

class OnlinePingApp extends StatelessWidget {
  const OnlinePingApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Online Ping Routing',
      theme: ThemeData.dark().copyWith(
        scaffoldBackgroundColor: const Color(0xFF090D16),
        primaryColor: const Color(0xFF06B6D4),
      ),
      home: const HomeScreen(),
    );
  }
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  bool isConnected = false;
  String statusMessage = "سرویس غیرفعال است";
  static const platform = MethodChannel('com.onlineping.routing/vpn');

  final TextEditingController inInterfaceController = TextEditingController(text: "eth0");
  final TextEditingController outInterfaceController = TextEditingController(text: "tun0");
  final TextEditingController portController = TextEditingController(text: "443");

  Future<void> toggleVpn() async {
    try {
      if (!isConnected) {
        final String result = await platform.invokeMethod('startVpn');
        setState(() {
          isConnected = true;
          statusMessage = "تونل Online Ping فعال شد ($result)";
        });
      } else {
        final String result = await platform.invokeMethod('stopVpn');
        setState(() {
          isConnected = false;
          statusMessage = "تونل متوقف شد ($result)";
        });
      }
    } on PlatformException catch (e) {
      setState(() {
        statusMessage = "خطا: ${e.message}";
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0,
        backgroundColor: const Color(0xFF0F172A),
        title: Row(
          children: [
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
              decoration: BoxDecoration(
                gradient: const LinearGradient(
                  colors: [Color(0xFF06B6D4), Color(0xFF3B82F6)],
                ),
                borderRadius: BorderRadius.circular(8),
              ),
              child: const Text('OP', style: TextStyle(fontWeight: FontWeight.bold, color: Colors.white, fontSize: 16)),
            ),
            const SizedBox(width: 12),
            const Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text('ONLINE PING', style: TextStyle(fontWeight: FontWeight.extrabold, fontSize: 16, color: Colors.white)),
                Text('Routing Engine', style: TextStyle(fontSize: 10, color: Colors.cyanAccent)),
              ],
            ),
          ],
        ),
      ),
      body: Directionality(
        textDirection: TextDirection.rtl,
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Container(
                padding: const EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: const Color(0xFF0F172A),
                  borderRadius: BorderRadius.circular(16),
                  border: Border.all(color: const Color(0xFF1E293B)),
                ),
                child: Row(
                  children: [
                    Container(
                      width: 12,
                      height: 12,
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        color: isConnected ? const Color(0xFF22C55E) : const Color(0xFFEF4444),
                        boxShadow: [
                          BoxShadow(
                            color: isConnected ? const Color(0xFF22C55E).withOpacity(0.6) : Colors.transparent,
                            blurRadius: 8,
                            spreadRadius: 2,
                          )
                        ],
                      ),
                    ),
                    const SizedBox(width: 12),
                    Expanded(
                      child: Text(
                        statusMessage,
                        style: TextStyle(
                          color: isConnected ? const Color(0xFF4ADE80) : const Color(0xFF94A3B8),
                          fontSize: 13,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 32),
              Center(
                child: GestureDetector(
                  onTap: toggleVpn,
                  child: AnimatedContainer(
                    duration: const Duration(milliseconds: 300),
                    width: 140,
                    height: 140,
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      gradient: isConnected
                          ? const LinearGradient(colors: [Color(0xFF06B6D4), Color(0xFF2563EB)])
                          : null,
                      color: isConnected ? null : const Color(0xFF0F172A),
                      boxShadow: isConnected
                          ? [
                              BoxShadow(
                                color: const Color(0xFF06B6D4).withOpacity(0.4),
                                blurRadius: 30,
                                spreadRadius: 5,
                              )
                            ]
                          : [],
                      border: Border.all(
                        color: isConnected ? Colors.cyanAccent : const Color(0xFF1E293B),
                        width: 2,
                      ),
                    ),
                    child: Icon(
                      Icons.power_settings_new_rounded,
                      size: 64,
                      color: isConnected ? Colors.white : Colors.grey[600],
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 36),
              Container(
                padding: const EdgeInsets.all(20),
                decoration: BoxDecoration(
                  color: const Color(0xFF0F172A),
                  borderRadius: BorderRadius.circular(20),
                  border: Border.all(color: const Color(0xFF1E293B)),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      "تنظیمات ترافیک شبکه",
                      style: TextStyle(fontSize: 14, fontWeight: FontWeight.bold, color: Colors.white),
                    ),
                    const SizedBox(height: 16),
                    _buildInputField("اینترفیس ورودی (In Interface)", inInterfaceController),
                    const SizedBox(height: 12),
                    _buildInputField("اینترفیس خروجی (Out Interface)", outInterfaceController),
                    const SizedBox(height: 12),
                    _buildInputField("پورت مدیریت ترافیک (Port)", portController),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildInputField(String label, TextEditingController controller) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(label, style: const TextStyle(fontSize: 11, color: Color(0xFF94A3B8))),
        const SizedBox(height: 6),
        TextField(
          controller: controller,
          style: const TextStyle(fontSize: 13, color: Colors.white),
          decoration: InputDecoration(
            filled: true,
            fillColor: const Color(0xFF090D16),
            contentPadding: const EdgeInsets.symmetric(horizontal: 14, vertical: 10),
            enabledBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10),
              borderSide: const BorderSide(color: Color(0xFF1E293B)),
            ),
            focusedBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10),
              borderSide: const BorderSide(color: Color(0xFF06B6D4)),
            ),
          ),
        ),
      ],
    );
  }
}
