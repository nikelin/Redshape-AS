<?xml version="1.0"?>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xhtml"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>WebCam Server API Annotation</title>
                <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
            </head>

            <body>
                <h1>API Interfaces</h1>

                <ul>
                    <xsl:apply-templates/>
                </ul>

                <xsl:call-template name="footer"/>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template name="footer">
        <div id='footer'>
            2010 Cambala Group(c)<br/>
            Generated by apiDocsGen via <a href="mailto:self@nikelin.ru">self@nikelin.ru</a>
        </div>
    </xsl:template>

    <xsl:template match="parameters">
        <tr>
            <td style="text-align: left;" colspan="3"><strong>Parameters</strong></td>
        </tr>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="interface">
        <h1>Controller <strong><xsl:value-of select="@name"/>( <xsl:value-of select="count(actions/action)"/> actions ) </strong></h1>
        <table class='actions'>
            <xsl:apply-templates/>
        </table>
    </xsl:template>

    <xsl:template match="action">
        <tr>
            <td class='title'>Action <xsl:value-of select="@name"/></td>
        </tr>
        
        <xsl:apply-templates select="request"/>
        <xsl:apply-templates select="response"/>
    </xsl:template>

    <xsl:template match="response">
        <tr>
            <td style='text-align:center; background-color:#000FFCC' colspan="3" ><strong>Response</strong></td>
        </tr>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="request">
        <tr>
            <td class="requester" colspan="3" style='text-align:center; background-color:#000FFCC'><strong>Request</strong></td>
        </tr>

        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="errors">
        <tr>
            <td class="errors" style="text-align:left;" colspan="3"><strong>Errors</strong></td>
        </tr>

        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="permissions">
        <tr>
            <td class="permissions"><strong>Permissions</strong></td>
        </tr>

        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="permission">
        <tr>
            <td class="permission"><xsl:value-of select="@name"/></td>
        </tr>
    </xsl:template>

    <xsl:template match="error">
        <tr>
            <td class="error"><xsl:value-of select="text()"/></td>
        </tr>
    </xsl:template>

    <xsl:template match="parameter">
        <tr class="parameter">
            <td><xsl:value-of select="@name"/></td>
            <td><xsl:value-of select="@type"/></td>
            <td><xsl:value-of select="@optinality"/></td>
        </tr>
    </xsl:template>
</xsl:stylesheet>