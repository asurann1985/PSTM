package com.xhlx;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatTabbedPaneCloseIcon;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.xhlx.pstm.component.PSTM;
import com.xhlx.pstm.component.PstmButton;
import com.xhlx.pstm.component.PstmRequestPanel;
import com.xhlx.pstm.component.Style;
import com.xhlx.pstm.component.tabstyle.PstmTabStyle;
import com.xhlx.pstm.model.PstmMethod;
import com.xhlx.pstm.model.PstmRequest;
import com.xhlx.pstm.model.attr.PstmHeaderItem;
import com.xhlx.pstm.model.attr.PstmQueryParamItem;

import static com.formdev.flatlaf.FlatClientProperties.*;
import static com.formdev.flatlaf.FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK;
import static com.formdev.flatlaf.FlatClientProperties.TABBED_PANE_TAB_CLOSE_TOOLTIPTEXT;

public class MainWind {
    
    public static MainWind window;

    public JFrame frame;
    
    public String currPath = "";
    
    public static Map<String, String> properties = new HashMap<>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        PSTM.setup();
        PSTM.setUseNativeWindowDecorations(true);
        UIManager.put( "TitlePane.menuBarEmbedded", true);
        UIManager.put( "TitlePane.unifiedBackground", true);
        UIManager.put( "TitlePane.showIcon", true);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try { 
                    window = new MainWind();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainWind() {
        
        currPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setMinimumSize(new Dimension(800, 500));
//		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        
        
        
        
        
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu();
        fileMenu.setText("File");
        fileMenu.setMnemonic('F');
        
        JMenuItem newMenuItem = new JMenuItem();

        newMenuItem.setText("New");
        newMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        newMenuItem.setMnemonic('N');
        fileMenu.add(newMenuItem);

        JMenuItem openMenuItem = new JMenuItem();

        openMenuItem.setText("Import");
        openMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        openMenuItem.setMnemonic('I');
        fileMenu.add(openMenuItem);

        JMenuItem importMenuItem = new JMenuItem();

        importMenuItem.setText("Save");
        importMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        importMenuItem.setMnemonic('S');
        fileMenu.add(importMenuItem);
        
        menuBar.add(fileMenu);
        
        menuBar.setVisible(true);
        
        frame.setJMenuBar(menuBar);
        
        
        JTabbedPane requestsPanel = new JTabbedPane();
        
        JButton muitAdd = new JButton(new FlatSVGIcon("addtab.svg", 25, 25));
        muitAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        muitAdd.setFocusable(false);
        muitAdd.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                PstmRequest req = new PstmRequest();

                PstmRequestPanel request = new PstmRequestPanel(req, requestsPanel);
                
                requestsPanel.add(request, "New Request");

                requestsPanel.setSelectedIndex(requestsPanel.getTabCount() - 1);
            }
        });
        
        JButton muitImport = new JButton(new FlatSVGIcon("import.svg", 25, 25));
        muitImport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        muitImport.setFocusable(false);
        muitImport.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                JFileChooser chooser = new JFileChooser(properties.get("lib.rootpath"));
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setDialogTitle("Import Request");
                chooser.setFileFilter(new FileNameExtensionFilter("Json File", "json"));
                
                
                int rst = chooser.showOpenDialog(frame);
                
                if (rst == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    
                    if (file.exists()) {
                        PstmRequest request = null;
                        try {
                            request = JSON.parseObject(new FileInputStream(file), PstmRequest.class);
                        } catch (FileNotFoundException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        
                        if (request != null) {
                            PstmRequestPanel newPanel = new PstmRequestPanel(request, requestsPanel);
                            
                            newPanel.setSaveFile(file);
                            
                            requestsPanel.add(newPanel, request.getUrl());
                            
                            requestsPanel.setSelectedIndex(requestsPanel.getTabCount() - 1);
                        }
                    }
                }
            }
            
        });
        
        JButton muitSave = new JButton(new FlatSVGIcon("downloadtab.svg", 25, 25));
//        muitSave.setText("Save");
//        muitSave.setPreferredSize(new Dimension(80, 30));
        muitSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        muitSave.setFocusable(false);
//        muitSave.setBackground(Style.saveBtColor);
        muitSave.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (requestsPanel.getSelectedComponent() != null && requestsPanel.getSelectedComponent() instanceof PstmRequestPanel) {
                    
                    // 如果是从文件加载的，就保存到加载的文件里，如果是新建的，就保存到新的文件里。
                    if (((PstmRequestPanel)requestsPanel.getSelectedComponent()).getSaveFile() == null) {
                    
                        int fileIdx = 1;
                        
                        for (;;fileIdx++) {
                            File f = new File(currPath + "/Request-" + fileIdx + ".json");
                            
                            if (!f.exists()) {
                                break;
                            }
                        }
                        
                        JFileChooser chooser = new JFileChooser(currPath);
                        
                        chooser.setMultiSelectionEnabled(false);
                        chooser.setDialogTitle("Save Request");
                        chooser.setSelectedFile(new File("Request-" + fileIdx + ".json"));
                        
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("Json File", "json");
                        chooser.setFileFilter(filter);
                        
                        int rst = chooser.showSaveDialog(frame);
                        
                        if (rst == JFileChooser.APPROVE_OPTION) {
                            
                            File fileToSave = chooser.getSelectedFile();
                            
                            if (!fileToSave.getName().toLowerCase().endsWith(".json")) {
                                fileToSave = new File(fileToSave.getAbsolutePath() + ".json");
                            }
                            
                            PrintWriter pw = null;
                            
                            try {
                                String str = JSON.toJSONString(((PstmRequestPanel)requestsPanel.getSelectedComponent()).getRequest(), JSONWriter.Feature.PrettyFormat);
                                System.out.println(str);
                                
                                pw = new PrintWriter(fileToSave);
                                pw.write(str);
                                pw.flush();
                            } catch (FileNotFoundException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }finally {
                                if (pw != null) {
                                    pw.close();
                                }
                            }
                            
                            
                        }
                    } else {
                        
                        File fileToSave = ((PstmRequestPanel)requestsPanel.getSelectedComponent()).getSaveFile();
                        
                        if (fileToSave.exists()) {
                            fileToSave.delete();
                        }
                        
                        PrintWriter pw = null;
                        
                        try {
                            String str = JSON.toJSONString(((PstmRequestPanel)requestsPanel.getSelectedComponent()).getRequest(), JSONWriter.Feature.PrettyFormat);
                            System.out.println(str);
                            
                            pw = new PrintWriter(fileToSave);
                            pw.write(str);
                            pw.flush();
                        } catch (FileNotFoundException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }finally {
                            if (pw != null) {
                                pw.close();
                            }
                        }
                    }
                }
            }
            
        });
        
        JToolBar trailing = new JToolBar();
        trailing.setFloatable( false );
        trailing.setBorder( null );
        trailing.add( Box.createHorizontalGlue() );
        trailing.add(muitAdd);
        trailing.add(muitImport);
        trailing.add(muitSave);

        
        requestsPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//        requestsPanel.setUI(new PstmTabStyle());
        requestsPanel.putClientProperty(TABBED_PANE_TAB_TYPE, TABBED_PANE_TAB_TYPE_CARD);
        requestsPanel.putClientProperty( TABBED_PANE_TAB_CLOSABLE, true );
        requestsPanel.putClientProperty( TABBED_PANE_TAB_CLOSE_TOOLTIPTEXT, "Close" );
        requestsPanel.putClientProperty( TABBED_PANE_MINIMUM_TAB_WIDTH, 170 );
        requestsPanel.putClientProperty( TABBED_PANE_MAXIMUM_TAB_WIDTH, 170 );
        requestsPanel.putClientProperty( TABBED_PANE_TAB_ALIGNMENT, SwingConstants.LEADING );
        requestsPanel.putClientProperty( TABBED_PANE_TAB_ICON_PLACEMENT, SwingConstants.LEADING );
        requestsPanel.putClientProperty( TABBED_PANE_TRAILING_COMPONENT, trailing );
        requestsPanel.putClientProperty( TABBED_PANE_SHOW_TAB_SEPARATORS, true);
        requestsPanel.putClientProperty( TABBED_PANE_TAB_CLOSE_CALLBACK,
                (BiConsumer<JTabbedPane, Integer>) (tabPane, tabIndex) -> {
//                    AWTEvent e = EventQueue.getCurrentEvent();
//                    int modifiers = (e instanceof MouseEvent) ? ((MouseEvent)e).getModifiers() : 0;
//                    JOptionPane.showMessageDialog( null, "Closed tab '" + tabPane.getTitleAt( tabIndex ) + "'."
//                                    + "\n\n(modifiers: " + MouseEvent.getMouseModifiersText( modifiers ) + ")",
//                            "Tab Closed", JOptionPane.PLAIN_MESSAGE );
                    requestsPanel.remove(tabIndex);
                } );
        
        // 切换tab的时候根据tab是否已经保存修改保存按钮图标
        requestsPanel.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                PstmRequestPanel reqPanel = (PstmRequestPanel) requestsPanel.getSelectedComponent();
                if (reqPanel.getSaveFile() != null) {
                    muitSave.setIcon(new FlatSVGIcon("replace.svg", 25, 25));
                } else {
                    muitSave.setIcon(new FlatSVGIcon("downloadtab.svg", 25, 25));
                }
            }
        });


//        PstmRequest req = new PstmRequest();
//        req.getHeaders().add(new PstmHeaderItem("a", "b"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//        req.getHeaders().add(new PstmHeaderItem("c", "d"));
//
//        req.getParams().add(new PstmQueryParamItem("e", "f"));
//
//        req.setMethod(PstmMethod.GET);
//        req.setUrl("www.baidu.com");
//
//        PstmRequestPanel request = new PstmRequestPanel(req, requestsPanel);
//
//        PstmRequest req2 = new PstmRequest();
//        PstmRequestPanel request2 = new PstmRequestPanel(req2, requestsPanel);
//
//        PstmRequest req21 = new PstmRequest();
//        PstmRequestPanel request21 = new PstmRequestPanel(req21, requestsPanel);
//
////        request.putClientProperty("JTabbedPane.tabCloseable", "");
//        requestsPanel.addTab("ssssss", request);
//        requestsPanel.addTab("ffffff", request2);
//        requestsPanel.addTab("ffffff", request21);
//        requestsPanel.add(request, "ssssss");
//        requestsPanel.add(request2, "ffffff");
//        requestsPanel.add(request21, "ffffff");

        frame.getContentPane().add(requestsPanel, BorderLayout.CENTER);
        
        // 初始化顶部工具栏
        GridBagLayout toolsbarlayout = new GridBagLayout();
        toolsbarlayout.columnWidths = new int[] {0,0,0,0,0,0,0,0,80,50};
        toolsbarlayout.rowHeights = new int[] {30};
        toolsbarlayout.columnWeights = new double[] {0.0,0.0,0.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0};
        GridBagConstraints toolsbarc = null;
        
//        JPanel toolsbar = new JPanel(toolsbarlayout);
//        toolsbar.setPreferredSize(new Dimension(0, 30));
//        frame.getContentPane().add(toolsbar, BorderLayout.NORTH);
//        
////        PstmButton muitOpen = new PstmButton("Open", new Dimension(80, 30));
//        JButton muitOpen = new JButton();
//        muitOpen.setText("Open");
//        muitOpen.setPreferredSize(new Dimension(80, 30));
//        muitOpen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        muitOpen.setFocusable(false);
//        toolsbarc = new GridBagConstraints();
//        toolsbarc.gridx = 0;
//        toolsbarc.gridy = 0;
//        toolsbar.add(muitOpen, toolsbarc);
//        
////        PstmButton muitImport = new PstmButton("Import", new Dimension(80, 30));
//        JButton muitImport = new JButton();
//        muitImport.setText("Import");
//        muitImport.setPreferredSize(new Dimension(80, 30));
//        muitImport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        muitImport.setFocusable(false);
//        toolsbarc = new GridBagConstraints();
//        toolsbarc.gridx = 1;
//        toolsbarc.gridy = 0;
//        toolsbar.add(muitImport, toolsbarc);

        // 新建tab按钮
//        PstmButton muitAdd = new PstmButton("New", new Dimension(80, 30));
//        JButton muitAdd = new JButton();
//        muitAdd.setText("New");
//        muitAdd.setPreferredSize(new Dimension(80, 30));
//        muitAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        muitAdd.setFocusable(false);
//        muitAdd.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                PstmRequest req = new PstmRequest();
//
//                PstmRequestPanel request = new PstmRequestPanel(req);
//                
//                requestsPanel.add(request, "New Request");
//
//                requestsPanel.setSelectedIndex(requestsPanel.getTabCount() - 1);
//            }
//
//        });
//        toolsbarc = new GridBagConstraints();
//        toolsbarc.gridx = 8;
//        toolsbarc.gridy = 0;
//        toolsbar.add(muitAdd, toolsbarc);
        
//        PstmButton muitSave = new PstmButton("Save", new Dimension(80, 30), Style.saveBtColor);
//        JButton muitSave = new JButton();
//        muitSave.setText("Save");
//        muitSave.setPreferredSize(new Dimension(80, 30));
//        muitSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        muitSave.setFocusable(false);
//        muitSave.setBackground(Style.saveBtColor);
//        muitSave.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (requestsPanel.getSelectedComponent() != null && requestsPanel.getSelectedComponent() instanceof PstmRequestPanel) {
//                    
//                    // 如果是从文件加载的，就保存到加载的文件里，如果是新建的，就保存到新的文件里。
//                    if (((PstmRequestPanel)requestsPanel.getSelectedComponent()).getSaveFile() == null) {
//                    
//                        int fileIdx = 1;
//                        
//                        for (;;fileIdx++) {
//                            File f = new File(currPath + "/Request-" + fileIdx + ".json");
//                            
//                            if (!f.exists()) {
//                                break;
//                            }
//                        }
//                        
//                        JFileChooser chooser = new JFileChooser(currPath);
//                        
//                        chooser.setMultiSelectionEnabled(false);
//                        chooser.setDialogTitle("Save Request");
//                        chooser.setSelectedFile(new File("Request-" + fileIdx + ".json"));
//                        
//                        FileNameExtensionFilter filter = new FileNameExtensionFilter("Json File", "json");
//                        chooser.setFileFilter(filter);
//                        
//                        int rst = chooser.showSaveDialog(frame);
//                        
//                        if (rst == JFileChooser.APPROVE_OPTION) {
//                            
//                            File fileToSave = chooser.getSelectedFile();
//                            
//                            if (!fileToSave.getName().toLowerCase().endsWith(".json")) {
//                                fileToSave = new File(fileToSave.getAbsolutePath() + ".json");
//                            }
//                            
//                            PrintWriter pw = null;
//                            
//                            try {
//                                String str = JSON.toJSONString(((PstmRequestPanel)requestsPanel.getSelectedComponent()).getRequest(), JSONWriter.Feature.PrettyFormat);
//                                System.out.println(str);
//                                
//                                pw = new PrintWriter(fileToSave);
//                                pw.write(str);
//                                pw.flush();
//                            } catch (FileNotFoundException e1) {
//                                // TODO Auto-generated catch block
//                                e1.printStackTrace();
//                            }finally {
//                                if (pw != null) {
//                                    pw.close();
//                                }
//                            }
//                            
//                            
//                        }
//                    } else {
//                        
//                        File fileToSave = ((PstmRequestPanel)requestsPanel.getSelectedComponent()).getSaveFile();
//                        
//                        if (fileToSave.exists()) {
//                            fileToSave.delete();
//                        }
//                        
//                        PrintWriter pw = null;
//                        
//                        try {
//                            String str = JSON.toJSONString(((PstmRequestPanel)requestsPanel.getSelectedComponent()).getRequest(), JSONWriter.Feature.PrettyFormat);
//                            System.out.println(str);
//                            
//                            pw = new PrintWriter(fileToSave);
//                            pw.write(str);
//                            pw.flush();
//                        } catch (FileNotFoundException e1) {
//                            // TODO Auto-generated catch block
//                            e1.printStackTrace();
//                        }finally {
//                            if (pw != null) {
//                                pw.close();
//                            }
//                        }
//                    }
//                }
//            }
//            
//        });
//        toolsbarc = new GridBagConstraints();
//        toolsbarc.gridx = 9;
//        toolsbarc.gridy = 0;
//        toolsbar.add(muitSave, toolsbarc);
        
        // 关闭tab按钮
//        PstmButton muitClose = new PstmButton("Close", new Dimension(50, 30), Style.closeRequestBtColor);
//        JButton muitClose = new JButton();
//        muitClose.setText("Close");
//        muitClose.setPreferredSize(new Dimension(80, 30));
//        muitClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        muitClose.setFocusable(false);
//        muitClose.setBackground(Style.closeRequestBtColor);
//        muitClose.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (requestsPanel.getSelectedComponent() != null) {
//                    requestsPanel.remove(requestsPanel.getSelectedComponent());
//                }
//            }
//        });
//        toolsbarc = new GridBagConstraints();
//        toolsbarc.gridx = 9;
//        toolsbarc.gridy = 0;
//        toolsbar.add(muitClose, toolsbarc);
    }

}

//class myst extends BasicTabbedPaneUI {
//
//	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
//		// 自定义选项卡的高：比默认的高度 高30
//		return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 10;
//	}
//
//	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
//		// 自定义选项卡的宽：比默认的宽度 宽50
//		return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 20;
//	}
//
//	/**
//	 * 自定义选项卡的背景色
//	 *
//	 * @param g            图形设置
//	 * @param tabPlacement 标签位置
//	 * @param tabIndex     标签下标
//	 * @param x            x轴
//	 * @param y            y轴
//	 * @param w            宽
//	 * @param h            高
//	 * @param isSelected   是否被选中
//	 */
//	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
//			boolean isSelected) {
//		Color defaultColor = new Color(164, 135, 217);
//		Color selectedColor = new Color(132, 99, 201);
//		// 设置选中时和未被选中时的颜色
//		g.setColor(!isSelected ? defaultColor : selectedColor);
//		// 填充图形，即选项卡为矩形
//		g.fillRect(x + 5, y + 5, w, h);
//	}
//
//	/**
//	 * 绘制标签的边框
//	 * 
//	 * @param g            图形
//	 * @param tabPlacement 标签位置
//	 * @param tabIndex     标签下标
//	 * @param x            x轴
//	 * @param y            y轴
//	 * @param w            宽
//	 * @param h            高
//	 * @param isSelected   标签是否被选中
//	 */
//	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
//			boolean isSelected) {// 自定义选项卡的边框色
//		// 注意：这个方法中的具体实现内容，可以写，也可以不写，根据自己实际情况决定。但这个方法定义必须有。否则会影响最终的显示效果
//		Color defaultColor = new Color(164, 135, 217);
//		Color selectedColor = new Color(132, 99, 201);
//		// 设置选中时和未被选中时的颜色
//		g.setColor(!isSelected ? defaultColor : selectedColor);
//		// 绘制边框，即选项卡边框为矩形
//		g.drawRect(x + 5, y + 5, w, h);
//	}
//
//	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title,
//			Rectangle textRect, boolean isSelected) {// 定义选项卡上的文本（颜色，字体，大小）
//
//		g.setColor(isSelected ? Color.white : new Color(189, 189, 189));
//		g.setFont(new Font("黑体", Font.BOLD, 18));
//
//		int h = calculateTabHeight(tabPlacement, tabIndex, 18);
//		int w = calculateTabWidth(tabPlacement, tabIndex, metrics);
//		// 分别在选项卡上画上文字。(文字的具体位置计算是根据定义的字体大小算出的)
//		if (tabIndex == 0) {
//			g.drawString(title, (w - 72) / 2 + 5, h / 2 + 11);
//		} else {
//			g.drawString(title, 3 * w / 2 - 28, h / 2 + 11);
//		}
//	}
//
//	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
//			Rectangle iconRect, Rectangle textRect, boolean isSelected) {// 这个方法定义如果没有的话，选项卡在选中时，内测会有虚线。
//		// Do nothing
//	}
//
//	protected LayoutManager createLayoutManager() {// 设置Layout
//		return new TabbedPaneLayout();
//	}
//
//	public class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {
//		// 要想实现：1.选中选项卡时，选项卡突出显示 2.选项卡之间有间距。那么必须重写以下方法！！
//		protected void calculateTabRects(int tabPlacement, int tabCount) {
//			super.calculateTabRects(tabPlacement, tabCount);
//			// 设置间距
//			setRec(5);
//		}
//
//		public void setRec(int rec) {
//			for (int i = 0; i < rects.length; i++) {
//				rects[i].x = rects[i].x + rec * i;
//			}
//		}
//	}
//
//}
